package org.nebezdari.IO;

import com.opencsv.exceptions.CsvValidationException;
import org.nebezdari.repositories.IRepository;

import java.io.IOException;

public interface IParser<T> {
    void fromFileToRepository(IRepository<T> repository, String filePath) throws IOException;
}
