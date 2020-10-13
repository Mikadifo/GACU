
package com.mikadifo.models.function_calls;

import com.mikadifo.models.DB_Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RandomImgForPlaceByCategory implements FunctionDB {

    private DB_Connection dbConnection = new DB_Connection();
    private final String functionName = "random_img_for_places_by_category_id(?)";
    private ResultSet results;
    private List<RandomImgForPlaceByCategory> resultList;

    private int categoryId;
    
    private int placeId;
    private String placeName;
    private String placeInfo; 
    private byte[] image;

    public RandomImgForPlaceByCategory(int categoryId) {
            this.categoryId = categoryId;
    }

    private RandomImgForPlaceByCategory(int placeId,
					String placeName,
					String placeInfo,
					byte[] image) {
        this.placeId = placeId;
        this.placeName = placeName;
	this.placeInfo = placeInfo;
        this.image = image;
    }

    @Override
    public List<RandomImgForPlaceByCategory> selectAll() {
        resultList = new ArrayList<>();
        
        try {
            dbConnection.buildAndPrepareSelect(functionName);
            dbConnection.getStatement().setInt(1, categoryId);
            results = dbConnection.executeQuery();

            while (results.next()) {                
                resultList.add(getObjectFromResulSet());
            }   

            results.close();
            dbConnection.closeStatement();
        } catch (SQLException e) {
            System.out.println("eee = " + e.getMessage());
            e.printStackTrace();
            return null;
        }

        return resultList;
    }
    
    private RandomImgForPlaceByCategory getObjectFromResulSet()
            throws SQLException{
        return new RandomImgForPlaceByCategory(
                results.getInt("place_id"),
                results.getString("place_name"),
                results.getString("place_info"),
                results.getBytes("image")
            );
    }

    public int getPlaceId() { return placeId; }

    public String getPlaceName() { return placeName; }

    public String getPlaceInfo() { return placeInfo; }

    public byte[] getImage() { return image; }

    @Override
    public String toString() {
        return  placeId + "---" + placeName + "---" + placeInfo + "---" + image[0];
    }
    
}
