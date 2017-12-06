package src.core;


public class Presenter<V> {
    private V view;

    protected V view() {
        return view;
    }

    public boolean hasView() {
        return view != null;
    }

    public void bindView(V view) {
        if (this.view != null) {
            throw new IllegalStateException("Presenter already has view!");
        }

        this.view = view;
    }
}
