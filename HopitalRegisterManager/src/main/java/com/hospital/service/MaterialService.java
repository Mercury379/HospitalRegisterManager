package com.hospital.service;

import com.hospital.pojo.Drug;
import com.hospital.pojo.Material;

import java.util.HashMap;

public interface MaterialService {
    HashMap<String, Object> findAllMaterial(int pageNumber, int size, String query);
    Material findMaterial(String mId);
}
