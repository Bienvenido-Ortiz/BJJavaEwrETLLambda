package gov.dot.nhtsa.odi.ewr.aws.s3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.ListVersionsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.services.s3.model.S3VersionSummary;
import com.amazonaws.services.s3.model.VersionListing;

/**
 * This is an AWS provided class. 
 * */
public class AwsS3Utils {
	/**
	 * Copy Object from one bucket to another
	 * 
	 * @param objectKey
	 * @param fromBucket
	 * @param toBucket
	 */
	public static void CopyObject(String objectKey, String fromBucket, String toBucket, String filename) {
		System.out.println("AwsS3Utils.CopyObject() --> Entering Method");
		System.out.format("AwsS3Utils.CopyObject() --> Copying object %s from bucket %s to %s\n", objectKey, fromBucket, toBucket);
		final AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();
		try {
			s3.copyObject(fromBucket, objectKey, toBucket, filename);
		} catch (AmazonServiceException e) {
			System.err.println(e.getErrorMessage());
			System.exit(1);
		}
		System.out.println("AwsS3Utils.CopyObject() - Object copied!");
	}

	public static Bucket getBucket(String bucket_name) {
		final AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();
		Bucket named_bucket = null;
		List<Bucket> buckets = s3.listBuckets();
		for (Bucket b : buckets) {
			if (b.getName().equals(bucket_name)) {
				named_bucket = b;
			}
		}
		return named_bucket;
	}

	public static File getObject(String bucket_name, String key_name) {
		System.out.format("Downloading %s from S3 bucket %s...\n", key_name, bucket_name);
		// final AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();
		AmazonS3 s3 = AmazonS3Client.builder().withRegion("us-east-1").withPathStyleAccessEnabled(true).build();
		try {
			S3Object o = s3.getObject(bucket_name, key_name);
			S3ObjectInputStream s3is = o.getObjectContent();
			File file = new File(key_name);
			FileOutputStream fos = new FileOutputStream(file);
			byte[] read_buf = new byte[1024];
			int read_len = 0;
			while ((read_len = s3is.read(read_buf)) > 0) {
				fos.write(read_buf, 0, read_len);
			}
			s3is.close();
			fos.close();
			return file;
		} catch (AmazonServiceException e) {
			System.err.println(e.getErrorMessage());
			return null;
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
			return null;
		} catch (IOException e) {
			System.err.println(e.getMessage());
			return null;
		}

	}

	public static File s3ObjectToFile(S3Object o, String key_name) {
		try {
			S3ObjectInputStream s3is = o.getObjectContent();
			File file = new File(key_name);
			FileOutputStream fos = new FileOutputStream(file);
			byte[] read_buf = new byte[1024];
			int read_len = 0;
			while ((read_len = s3is.read(read_buf)) > 0) {
				fos.write(read_buf, 0, read_len);
			}
			s3is.close();
			fos.close();
			return file;
		} catch (AmazonServiceException e) {
			System.err.println(e.getErrorMessage());
			return null;
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
			return null;
		} catch (IOException e) {
			System.err.println(e.getMessage());
			return null;
		}
	}

	public static Bucket createBucket(String bucket_name) {
		final AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();
		Bucket b = null;
		if (s3.doesBucketExistV2(bucket_name)) {
			System.out.format("Bucket %s already exists.\n", bucket_name);
			b = getBucket(bucket_name);
		} else {
			try {
				b = s3.createBucket(bucket_name);
			} catch (AmazonS3Exception e) {
				System.err.println(e.getErrorMessage());
			}
		}
		return b;
	}

	public static void deleteBucket(String bucket_name) {
		System.out.println("Deleting S3 bucket: " + bucket_name);
		final AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();

		try {
			System.out.println(" - removing objects from bucket");
			ObjectListing object_listing = s3.listObjects(bucket_name);
			while (true) {
				for (Iterator<?> iterator = object_listing.getObjectSummaries().iterator(); iterator.hasNext();) {
					S3ObjectSummary summary = (S3ObjectSummary) iterator.next();
					s3.deleteObject(bucket_name, summary.getKey());
				}

				// more object_listing to retrieve?
				if (object_listing.isTruncated()) {
					object_listing = s3.listNextBatchOfObjects(object_listing);
				} else {
					break;
				}
			}
			;

			System.out.println(" - removing versions from bucket");
			VersionListing version_listing = s3.listVersions(new ListVersionsRequest().withBucketName(bucket_name));
			while (true) {
				for (Iterator<?> iterator = version_listing.getVersionSummaries().iterator(); iterator.hasNext();) {
					S3VersionSummary vs = (S3VersionSummary) iterator.next();
					s3.deleteVersion(bucket_name, vs.getKey(), vs.getVersionId());
				}

				if (version_listing.isTruncated()) {
					version_listing = s3.listNextBatchOfVersions(version_listing);
				} else {
					break;
				}
			}

			System.out.println(" OK, bucket ready to delete!");
			s3.deleteBucket(bucket_name);
		} catch (AmazonServiceException e) {
			System.err.println(e.getErrorMessage());
			System.exit(1);
		}
		System.out.println("Done!");
	}

	public static void deleteBucketPolicy(String bucket_name) {
		System.out.format("Deleting policy from bucket: \"%s\"\n\n", bucket_name);

		final AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();
		try {
			s3.deleteBucketPolicy(bucket_name);
		} catch (AmazonServiceException e) {
			System.err.println(e.getErrorMessage());
			System.exit(1);
		}

		System.out.println("Done!");
	}

	public static void deleteObject(String object_key, String bucket_name) {
		System.out.println("AwsS3Utils.deleteObject() --> Entering Method");
		System.out.format("AwsS3Utils.deleteObject() --> Deleting object with key: [%s] from S3 bucket: [%s]\n", object_key, bucket_name);
		final AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();
		try {
			s3.deleteObject(bucket_name, object_key);
		} catch (AmazonServiceException e) {
			System.err.println(e.getErrorMessage());
			System.exit(1);
		}
		System.out.println("AwsS3Utils.deleteObject() --> Object Deleted!");
	}

	public static void deleteObjects(String[] object_keys, String bucket_name) {
		System.out.println("Deleting objects from S3 bucket: " + bucket_name);
		for (String k : object_keys) {
			System.out.println(" * " + k);
		}

		final AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();
		try {
			DeleteObjectsRequest dor = new DeleteObjectsRequest(bucket_name).withKeys(object_keys);
			s3.deleteObjects(dor);
		} catch (AmazonServiceException e) {
			System.err.println(e.getErrorMessage());
			System.exit(1);
		}
		System.out.println("Done!");
	}

	public static List<Bucket> listBuckets() {
		AmazonS3 s3 = AmazonS3Client.builder().withRegion("us-east-1").withPathStyleAccessEnabled(true).build();
		List<Bucket> buckets = s3.listBuckets();
		System.out.println("Your Amazon S3 buckets are:");
		return buckets;
	}

	public static List<S3ObjectSummary> ListObjects(String bucket_name) {
		System.out.format("Objects in S3 bucket %s:\n", bucket_name);
		AmazonS3 s3 = AmazonS3Client.builder().withRegion("us-east-1").withPathStyleAccessEnabled(true).build();
		ListObjectsV2Result result = s3.listObjectsV2(bucket_name);
		List<S3ObjectSummary> objects = result.getObjectSummaries();
		return objects;
	}

	public static List<String> ListObjectKeys(List<S3ObjectSummary> list) {
		List<String> keyList = new ArrayList<String>();
		Iterator<S3ObjectSummary> iter = list.iterator();
		while (iter.hasNext()) {
			keyList.add(iter.next().getKey());
		}
		return keyList;
	}

	public static List<String> ListOfKeys(String bucket_name) {
		System.out.format("Objects in S3 bucket %s:\n", bucket_name);
		AmazonS3 s3 = AmazonS3Client.builder().withRegion("us-east-1").withPathStyleAccessEnabled(true).build();
		ListObjectsV2Result result = s3.listObjectsV2(bucket_name);
		List<S3ObjectSummary> objects = result.getObjectSummaries();
		return ListObjectKeys(objects);
	}

	public static void putObject(String file_path, String bucket_name) {
		String key_name = Paths.get(file_path).getFileName().toString();
		System.out.format("Uploading %s to S3 bucket %s...\n", file_path, bucket_name);
		final AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();
		try {
			s3.putObject(bucket_name, key_name, new File(file_path));
		} catch (AmazonServiceException e) {
			System.err.println(e.getErrorMessage());
			System.exit(1);
		}
		System.out.println("Done!");
	}
}
