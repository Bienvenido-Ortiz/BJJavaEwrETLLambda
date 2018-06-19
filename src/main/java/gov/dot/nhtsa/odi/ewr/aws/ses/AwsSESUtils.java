package gov.dot.nhtsa.odi.ewr.aws.ses;
/*
 * Copyright 2014-2017 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;

import gov.dot.nhtsa.odi.ewr.aws.utils.EwrConstants;

public class AwsSESUtils {


	/**
	 * @author Bienvenido Ortiz
	 */
    public static void sendNotification(String bodyIn) {

        // Construct an object to contain the recipient address.
        Destination destination = new Destination().withToAddresses(new String[]{EwrConstants.TO});

        // Create the subject and body of the message.
        Content subject = new Content().withData(EwrConstants.SUBJECT);
        Content textBody = new Content().withData(bodyIn);
        Body body = new Body().withText(textBody);

        // Create a message with the specified subject and body.
        Message message = new Message().withSubject(subject).withBody(body);

        // Assemble the email.
        SendEmailRequest request = new SendEmailRequest().withSource(EwrConstants.FROM).withDestination(destination).withMessage(message);

        try {
            //System.out.println("Attempting to send an email through Amazon SES by using the AWS SDK for Java...");

            // Instantiate an Amazon SES client, which will make the service call with the supplied AWS credentials.
             AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder.defaultClient();
            // Send the email.
            client.sendEmail(request);

        } catch (Exception ex) {
            System.out.println("The email was not sent.");
            System.out.println("Error message: " + ex.getMessage());
        }
    }
}
