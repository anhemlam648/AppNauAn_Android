package android.vutrungnghia.appnauan.DTO;

import java.io.Serializable;

public class RecipeModel implements Serializable {
    private int id;
    private String name;
    private String author;
    private String datePosted;
    private String content;
    private byte[] imageBytes;
    private int userId;
    private int difficulty;

    public RecipeModel() {
    }

    public RecipeModel(int id, String name, String author, String datePosted, String content, byte[] imageBytes, int userId, int difficulty) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.datePosted = datePosted;
        this.content = content;
        this.imageBytes = imageBytes;
        this.userId = userId;
        this.difficulty = difficulty;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(String datePosted) {
        this.datePosted = datePosted;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public byte[] getImageBytes() {
        return imageBytes;
    }

    public void setImageBytes(byte[] imageBytes) {
        this.imageBytes = imageBytes;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
}
