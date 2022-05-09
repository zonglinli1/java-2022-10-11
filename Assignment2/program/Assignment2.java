import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class Assignment2 {
    public static void main(String[] args){
        Criteria criteria = new Criteria("test", "txt", false);
        try{
            count(criteria);
        }catch (FileNotFoundException e){
            System.out.println(e.getMessage());
        }
    }
    public static void count(Criteria criteria) throws FileNotFoundException{
        File dir = new File(criteria.getFolderPath());

        if (!dir.exists() || dir.isFile()) throw new FileNotFoundException("no folder was found");
        List<File> files = new LinkedList<>();
        List<File> folders = new LinkedList<>();

        if(criteria.isIncludeSubFolder()){
            countRecursive(dir, criteria.getExtension(), files, folders);
        }else{
            for (final File fileEntry : dir.listFiles()) {
                String extension = getExtension(fileEntry.getName()).orElse("");
                if ((criteria.getExtension() == null || extension.equals(criteria.getExtension())) && fileEntry.isDirectory()) {
                    folders.add(fileEntry);
                } else if((criteria.getExtension() == null || extension.equals(criteria.getExtension())) && fileEntry.isFile()) {
                    files.add(fileEntry);
                }
            }
        }

        System.out.format("There are %d file(s) and %d folder(s) inside folder %s ",
                files.size(),
                folders.size(),
                criteria.getFolderPath());
        if(criteria.getExtension() != null)
            System.out.println("with extension " + criteria.getExtension());
    }

    public static Optional<String> getExtension(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }

    public static void countRecursive(final File folder, String extension, List<File> files, List<File> folders) {

        for (final File fileEntry : folder.listFiles()) {
            String ext = getExtension(fileEntry.getName()).orElse("");
            if ((extension == null || ext.equals(extension)) && fileEntry.isDirectory()) {
                folders.add(fileEntry);
                countRecursive(fileEntry, extension, files, folders);
            } else if((extension == null || ext.equals(extension)) && fileEntry.isFile()) {
                files.add(fileEntry);
            }else if(fileEntry.isDirectory()){
                countRecursive(fileEntry, extension, files, folders);
            }
        }
    }
}

class Criteria{
    private String folderPath;
    private boolean includeSubFolder = false;
    private String extension;


    public Criteria(String folderPath){
        this.folderPath = folderPath;
    }

    public Criteria(String folderPath,String extension){
        this.folderPath = folderPath;
        this.extension = extension;
    }

    public Criteria(String folderPath, String extension, boolean includeSubFolder){
        this.folderPath = folderPath;
        this.includeSubFolder = includeSubFolder;
        this.extension = extension;
    }

    public String getFolderPath() {
        return folderPath;
    }

    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }

    public boolean isIncludeSubFolder() {
        return includeSubFolder;
    }

    public void setIncludeSubFolder(boolean includeSubFolder) {
        this.includeSubFolder = includeSubFolder;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
}

