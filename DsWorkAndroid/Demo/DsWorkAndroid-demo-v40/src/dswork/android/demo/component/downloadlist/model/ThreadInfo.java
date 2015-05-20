package dswork.android.demo.component.downloadlist.model;

/**
 * Created by ole on 15/5/8.
 */
public class ThreadInfo {
    private long id;
    private String url;
    private long start;
    private long end;
    private long finished;

    public ThreadInfo() {
        super();
    }

    public ThreadInfo(int id, String url, long start, long end, long finished)
    {
        this.id = id;
        this.url = url;
        this.start = start;
        this.end = end;
        this.finished = finished;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() { return end; }

    public void setEnd(long end) { this.end = end; }

    public long getFinished() {
        return finished;
    }

    public void setFinished(long finished) {
        this.finished = finished;
    }

    @Override
    public String toString() {
        return "ThreadInfo{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", finished=" + finished +
                '}';
    }
}
