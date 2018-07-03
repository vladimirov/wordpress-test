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

    public Posts(Collection<PostData> posts) {
        this.delegate = new HashSet<PostData>(posts);
    }

    @Override
    protected Set<PostData> delegate() {
        return delegate;
    }

    public Posts withAdded(PostData post) {
        Posts posts = new Posts(this);
        posts.add(post);
        return posts;
    }

    public Posts without(PostData post) {
        Posts posts = new Posts(this);
        posts.remove(post);
        return posts;
    }

}