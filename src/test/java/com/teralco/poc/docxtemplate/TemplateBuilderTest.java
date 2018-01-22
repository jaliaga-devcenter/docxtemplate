package com.teralco.poc.docxtemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.wickedsource.docxstamper.replace.typeresolver.image.Image;

public class TemplateBuilderTest {

	@Test
	public void generateTest() throws Exception {

		Data data = new Data();
		data.titulo = "Template generation";
		data.imagen = new Image(TemplateBuilderTest.class.getResourceAsStream("demo.jpg"));
		InputStream resourceAsStream = TemplateBuilderTest.class.getResourceAsStream("plantilla.docx");

		File yourFile = new File(this.getFilePath("output.docx"));
		yourFile.createNewFile(); // if file already exists will do nothing
		FileOutputStream oFile = new FileOutputStream(yourFile, false);

		TemplateBuilder.generate(resourceAsStream, oFile, data);
		IOUtils.closeQuietly(oFile);
		IOUtils.closeQuietly(resourceAsStream);

		TemplateBuilder.toPdf(new FileInputStream(yourFile), new File(this.getFilePath("output.pdf")));

		Assert.assertTrue(true);

	}

	@Test
	public void pymeDreamsTest() throws Exception {

		Data data = new Data();
		data.titulo = "El Rincón de María";
		data.imagen = new Image(TemplateBuilderTest.class.getResourceAsStream("pymedreams.jpg"));
		InputStream resourceAsStream = TemplateBuilderTest.class.getResourceAsStream("plantilla_pymedreams.docx");

		File yourFile = new File(this.getFilePath("pymedreams.docx"));
		yourFile.createNewFile(); // if file already exists will do nothing
		FileOutputStream oFile = new FileOutputStream(yourFile, false);

		TemplateBuilder.generate(resourceAsStream, oFile, data);
		IOUtils.closeQuietly(oFile);
		IOUtils.closeQuietly(resourceAsStream);

		TemplateBuilder.toPdf(new FileInputStream(yourFile), new File(this.getFilePath("pymedreams.pdf")));

		Assert.assertTrue(true);

	}

	private String getFilePath(String name) {

		return System.getProperty("user.home") + "/tmp/" + name;

	}

}
