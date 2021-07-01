package information;

import java.io.Serializable;

public abstract class Information implements Serializable {
    protected String path = "src/file/";
    private static final long serialVersionUID = 2037681803932504388L;


    // load and dump the data from or into the file
    protected abstract void load();
    protected abstract void dump();
}
