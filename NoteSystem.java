import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Scanner;

public class NoteSystem {
    private static final String FILE_PATH = "notes.ser";
    private HashMap<String, Note> notes = new HashMap<>();

    // load notes from file and save changes whenever program ends.
    public NoteSystem() {
        loadNotes(FILE_PATH);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> saveNotes(FILE_PATH)));
    }

    public void addNote(String title) throws NoteExistException {
        if (this.containsNote(title)) {
            throw new NoteExistException("Note already exists");
        }
        Note note = new Note(title);
        notes.put(title, note);
    }

    public void addContentToNote(String title) {
        Note note = notes.get(title);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String content = scanner.nextLine();
            if (content.equals("#")) {
                break;
            }

            note.addContent(content);
        }
    }

    public void removeNote(String title) throws NoteNotFoundException {
        if (!this.containsNote(title)) {
            throw new NoteNotFoundException("Note not found!");
        }

        notes.remove(title);
    }

    public boolean containsNote(String title) {
        return notes.containsKey(title);
    }

    public void saveNotes(String filePath) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filePath))) {
            outputStream.writeObject(notes);
            System.out.println("Notes saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving notes: " + e.getMessage());
        }
    }

    public void loadNotes(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filePath))) {
                Object object = inputStream.readObject();
                if (object instanceof HashMap) {
                    notes = (HashMap<String, Note>) object;
                    System.out.println("Notes loaded successfully.");
                } else {
                    System.err.println("Invalid file format.");
                }
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error loading notes: " + e.getMessage());
            }
        } else {
            System.out.println("No notes file found. Starting with an empty notebook.");
        }
    }

    public void displayNotes() {
        int i = 0;
        for (String title : notes.keySet()) {
            i++;
            Note note = notes.get(title);
            LocalDateTime creationDate = note.getCreationDate();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedCreationDate = creationDate.format(formatter);
            System.out.println(i + "- " + title + "    " + formattedCreationDate);
        }
    }

    public Note getNoteByTitle(String title) throws NoteNotFoundException {
        Note note = notes.get(title);
        if (note == null) {
            throw new NoteNotFoundException("Note not found!");
        }

        return note;
    }

    public HashMap<String, Note> getNotes() {
        return notes;
    }
}
