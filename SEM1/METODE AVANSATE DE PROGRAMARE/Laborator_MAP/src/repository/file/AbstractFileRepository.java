package repository.file;

import domain.Entity;
import repository.memory.InMemoryRepository;

import java.io.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;


public abstract class AbstractFileRepository<ID, E extends Entity<ID>> extends InMemoryRepository<ID, E> {
    String fileName;

    public AbstractFileRepository(String fileName) {
        this.fileName = fileName;
        loadData();
    }

    private void loadData() {
        Path path = Paths.get(fileName);
        try {
            List<String> lines = Files.readAllLines(path);
            lines.forEach(linie -> {
                E entity = extractEntity(Arrays.asList(linie.split(";")));
                super.save(entity);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public abstract E extractEntity(List<String> attributes);

    public abstract String encapsulateEntity(E entity);

    @Override
    public void save(E entity) {
        super.save(entity);
        writeToFile();
    }

    @Override
    public void delete(ID id) {
        super.delete(id);
        writeToFile();
    }

    @Override
    public void update(E entity, E newEntity) {
        super.update(entity, newEntity);
        writeToFile();
    }

    private void writeToFile() {
        Path path = Paths.get(fileName);
        File newFile = new File(path.toUri());

        try {
            FileWriter fileWriter = new FileWriter(newFile, false);
            Iterable<E> entities = super.findAll();
            for (E entity : entities) {
                fileWriter.write(encapsulateEntity(entity));
            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

