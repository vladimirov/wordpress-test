package model;

import com.google.common.collect.ForwardingSet;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Posts extends ForwardingSet<PostData> {

    private Set<PostData> delegate;

    public Posts(Posts posts) {
        this.delegate = new HashSet<PostData>(posts.delegate);
    }

    public Posts() {
        this.delegate = new HashSet<PostData>();
    }

    public Posts(Collection<PostData> groups) {
        this.delegate = new HashSet<PostData>(groups);
    }

    @Override
    protected Set<PostData> delegate() {
        return delegate;
    }

    public Posts withAdded(PostData group) {
        Posts groups = new Posts(this);
        groups.add(group);
        return groups;
    }

    public Posts without(PostData group) {
        Posts groups = new Posts(this);
        groups.remove(group);
        return groups;
    }

}
