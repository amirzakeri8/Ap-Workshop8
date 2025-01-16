import java.util.Scanner;

public class NoteBookApp {
    public void launch() {
        NoteSystem noteSystem = new NoteSystem();
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        while (choice != 5) {
            System.out.println();
            System.out.println("1. Add Note");
            System.out.println("2. Delete Note");
            System.out.println("3. Display Note");
            System.out.println("4. Export Note");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            // Handling invalid options.
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Invalid option!");
                scanner.nextLine();
                continue;
            }
            if (choice <= 0 || choice > 5) {
                System.out.println("Invalid option!");
                continue;
            }

            // Adding new Note to NoteBook
            if (choice == 1) {
                System.out.println("Choose a title to add note: ");
                System.out.println("You can enter -1 to stop this process...");
                String title = scanner.nextLine();
                if (!title.equals("-1")) {
                    try {
                        noteSystem.addNote(title);
                        System.out.println("now write to add Contents...");
                        System.out.println("You can stop writing whenever you enter '#'");
                        noteSystem.addContentToNote(title);
                        System.out.println("Note added successfully!");
                    } catch (NoteExistException e) {
                        System.out.println(e.getMessage());
                    }
                }
            // Deleting Note by its title
            } else if (choice == 2) {
                System.out.println("Choose a title to delete note: ");
                noteSystem.displayNotes();
                System.out.println("You can enter -1 to stop this process...");

                String title = scanner.nextLine();
                if (!title.equals("-1")) {
                    try {
                        noteSystem.removeNote(title);
                        System.out.println("Note deleted successfully!");
                    } catch (NoteNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                }
            // Displaying a Note by its title
            } else if (choice == 3) {
                System.out.println("Choose a note by title to display its content!");
                noteSystem.displayNotes();
                System.out.println("You can enter -1 to stop this process...");
                String title = scanner.nextLine();

                if (!title.equals("-1")) {
                    try {
                        Note note = noteSystem.getNoteByTitle(title);
                        note.displayNote();
                    } catch (NoteNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                }
            // Exporting a Note to .txt file by its title.
            } else if (choice == 4) {
                System.out.println("Choose a note by title to export its content!");
                noteSystem.displayNotes();
                System.out.println("You can enter -1 to stop this process...");
                String title = scanner.nextLine();

                if (!title.equals("-1")) {
                    try {
                        Note note = noteSystem.getNoteByTitle(title);
                        note.exportToFile(title);
                    } catch (NoteNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }

        }
    }
}
