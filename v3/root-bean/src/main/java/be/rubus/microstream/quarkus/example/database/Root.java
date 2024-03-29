package be.rubus.microstream.quarkus.example.database;

import be.rubus.microstream.quarkus.example.model.Book;
import be.rubus.microstream.quarkus.example.model.User;
import one.microstream.integrations.quarkus.types.Storage;
import one.microstream.storage.types.StorageManager;

import jakarta.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Storage
public class Root {

    @Inject
    transient StorageManager storageManager;

    private final List<User> users = new ArrayList<>();
    private final List<Book> books = new ArrayList<>();

    public List<User> getUsers() {
        return new ArrayList<>(users);
    }

    public User addUser(User user) {
        users.add(user);
        storageManager.store(users);
        return user;
    }

    /**
     * Since the User instance is already part of the User Collection, we just need
     * to make it is stored externally.
     *
     * @param user
     */
    public void updateUser(User user) {
        storageManager.store(user);
    }

    public void removeUser(User user) {
        users.remove(user);
        storageManager.store(users);
    }

    public List<Book> getBooks() {
        return new ArrayList<>(books);
    }

    public void addBook(Book book) {
        books.add(book);
        storageManager.store(books);
    }

    /**
     * User instance must already be part of the Object graph of the root managed by MicroStream.
     *
     * @param user
     * @param book
     */
    public void addBookToUser(User user, Book book) {
        user.addBook(book, storageManager);
    }
}
