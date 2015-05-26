package dswork.android.demo.component.downloadlist.model;

import java.io.Serializable;

import dswork.android.lib.core.db.BaseModel;

/**
 * Created by ole on 15/5/8.
 */
public class FileInfo extends BaseModel implements Serializable
{
    private int file_id;
    private String url;
    private String filename;
    private long length;
    private long finished;
    private int progress;
    private String status;

    public FileInfo() {
    }

    public FileInfo(int file_id, String url, String filename, long length, long finished, int progress, String status)
    {
        this.file_id = file_id;
        this.url = url;
        this.filename = filename;
        this.length = length;
        this.finished = finished;
        this.progress = progress;
        this.status = status;
    }

    public int getFile_id() {
        return file_id;
    }

    public void setFile_id(int file_id) {
        this.file_id = file_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String fileName) {
        this.filename = fileName;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public long getFinished() {
        return finished;
    }

    public void setFinished(long finished) {
        this.finished = finished;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString()
    {
        return "FileInfo{" +
                "file_id=" + file_id +
                ", url='" + url + '\'' +
                ", filename='" + filename + '\'' +
                ", length=" + length +
                ", finished=" + finished +
                ", progress=" + progress +
                ", status='" + status + '\'' +
                '}';
    }
}
