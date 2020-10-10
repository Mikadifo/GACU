package com.mikadifo.models.function_calls;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mikadifo.models.DB_Connection;

public class RandomImgForCategory implements FunctionDB {

    private DB_Connection dbConnection = new DB_Connection();
    private final String functionName = "random_img_for_category()";
    private ResultSet results;
    private List<RandomImgForCategory> resultList;

    private int categoryId;
    private String categoryName;
    private byte[] randomImage;

    public RandomImgForCategory() { }

    private RandomImgForCategory(int categoryId,
				String categoryName,
				byte[] randomImage) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.randomImage = randomImage;
    }
    

    @Override
    public List<RandomImgForCategory> selectAll() {
	resultList = new ArrayList<>();

        try {
	    dbConnection.buildAndPrepareSelect(functionName);
            results = dbConnection.executeQuery();
	    
	    while(results.next()) {
                resultList.add(getObjectFromResultSet());
            }    
	    
	    results.close();
            dbConnection.closeStatement();
        } catch (SQLException ex) {
	   return null;
	}

	return  resultList;
    }

    private RandomImgForCategory getObjectFromResultSet() 
	    throws SQLException {
	return new RandomImgForCategory(
		results.getInt("category_id"), 
		results.getString("category_name"), 
		results.getBytes("image")
	);
    }


    public int getCategoryId() { return categoryId; }

    public String getCategoryName() { return categoryName; }

    public byte[] getRandomImage() { return randomImage; }

    @Override
    public String toString() {
	return this.categoryName + " " + this.randomImage[0] + " " + this.categoryId;
    }

}

