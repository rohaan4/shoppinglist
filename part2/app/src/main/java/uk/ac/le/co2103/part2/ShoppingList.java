package uk.ac.le.co2103.part2;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "shopping_list")

public class ShoppingList {
    @PrimaryKey(autoGenerate = true)
    private int listId; // Unique identifier
    @ColumnInfo(name = "name")
    private String name;
    private String image; // Path to the image or URI

    public ShoppingList(int listId, String name, String image) {
        this.listId = listId;
        this.name = name;
        this.image = image;
    }


    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Constructor, getters, and setters
}
