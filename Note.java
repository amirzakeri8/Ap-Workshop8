import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class Note implements Serializable {
    private static final long serialVersionUID = 1L;
    private String title;
    private final ArrayList<String> content = new ArrayList<>();
    private LocalDateTime creationDate;

    public Note(String title) {
        this.title = title;
        this.creationDate = LocalDateTime.now();
    }

    public void displayNote() {
        System.out.println("------  " + this.title + "  ------");
        for (String content : this.content) {
            System.out.println(content);
        }
    }

    public void addContent(String content) {
        this.content.add(content);
    }

    public void exportToFile(String fileName) {
        File directory = new File("exports");
        if (!directory.exists()) {
            directory.mkdir();
        }

        File file = new File(directory, fileName + ".txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-d-M");
            writer.write("Title: " + this.title + "\n");
            writer.write("Creation Date: " + this.creationDate.format(formatter) + "\n");
            writer.write("\nContent:\n");
            for (String line : this.content) {
                writer.write(line + "\n");
            }
            System.out.println("Note exported to " + file.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error exporting note: " + e.getMessage());
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public ArrayList<String> getContent() {
        return content;
    }
}
