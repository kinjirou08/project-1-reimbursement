package com.revature.dao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import com.revature.models.Reimbursement;

public class ReceiptMaker {

	private ReceiptMaker() {
		throw new IllegalStateException("Utility class");
	}

	public static InputStream makeReceipt(Reimbursement info) throws IOException {

		int bufferSize = 8 * 1024;

		try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("d:/test.txt"), "UTF-8"),
				bufferSize)) {
			
			writer.write("Reimbursement Author:" + info.getReimbAuthor());
			((BufferedWriter) writer).newLine();
			writer.write("Time of creation of the Reimbursement request: ");
			((BufferedWriter) writer).newLine();
			writer.write(info.getReimbSubmitted());
			((BufferedWriter) writer).newLine();
			writer.write("-----------------------------------");
			((BufferedWriter) writer).newLine();
			writer.write("Reimbursement ID: " + info.getReimbId());
			((BufferedWriter) writer).newLine();
			writer.write("Type of Reimbursement: " + info.getReimbType());
			((BufferedWriter) writer).newLine();
			writer.write("Reason for Reimbursement: " + info.getReimbDescription());
			((BufferedWriter) writer).newLine();
			writer.write("Reimbursement Amount: " + info.getReimbAmount());
			((BufferedWriter) writer).newLine();
			writer.write("Approved or Rejected?: " + info.getReimbStatus());
			((BufferedWriter) writer).newLine();
			writer.write("Time of request resolved: " + info.getReimbResolved());
			((BufferedWriter) writer).newLine();
			writer.write("Reimbursement Resolver:" + info.getReimbResolver());

			writer.flush();
			writer.close();

			File initialFile = new File("d:/test.txt");
			InputStream targetStream = new FileInputStream(initialFile);

			return targetStream;
		}
	}
}
