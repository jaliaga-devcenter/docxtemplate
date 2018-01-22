package com.teralco.poc.docxtemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.docx4j.Docx4J;
import org.docx4j.convert.out.FOSettings;
import org.docx4j.fonts.IdentityPlusMapper;
import org.docx4j.fonts.Mapper;
import org.docx4j.fonts.PhysicalFonts;
import org.docx4j.model.fields.FieldUpdater;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.wickedsource.docxstamper.DocxStamper;
import org.wickedsource.docxstamper.DocxStamperConfiguration;

public final class TemplateBuilder {

	private TemplateBuilder() {

	}

	public static void generate(InputStream template, OutputStream out, Data data) {
		DocxStamper stamper = new DocxStamperConfiguration().build();
		stamper.stamp(template, data, out);

	}

	public static void toPdf(InputStream inputStream, File output) throws Exception {
		String regex = null;
		PhysicalFonts.setRegex(regex);
		WordprocessingMLPackage wordMLPackage;
		wordMLPackage = WordprocessingMLPackage.load(inputStream);
		FieldUpdater updater = new FieldUpdater(wordMLPackage);
		updater.update(true);
		OutputStream os = new FileOutputStream(output);

		Mapper fontMapper = new IdentityPlusMapper();
		wordMLPackage.setFontMapper(fontMapper);

		FOSettings foSettings = Docx4J.createFOSettings();
		foSettings.setWmlPackage(wordMLPackage);

		Docx4J.toFO(foSettings, os, Docx4J.FLAG_EXPORT_PREFER_XSL);

		if (wordMLPackage.getMainDocumentPart().getFontTablePart() != null) {
			wordMLPackage.getMainDocumentPart().getFontTablePart().deleteEmbeddedFontTempFiles();
		}

		updater = null;
		foSettings = null;
		wordMLPackage = null;

	}

}
