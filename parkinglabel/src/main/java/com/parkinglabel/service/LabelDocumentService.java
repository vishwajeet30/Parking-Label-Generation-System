package com.parkinglabel.service;

import java.util.List;

import com.parkinglabel.model.LabelDocument;

public interface LabelDocumentService {

	List<LabelDocument> findAll();
	
	LabelDocument findById(int id);
	
	void saveDocument(LabelDocument document);
	
	List<LabelDocument> findAllByLabelId(int labelId);
	
	void deleteById(int id);
	
	void deleteAllByLabelId(int id);
}

