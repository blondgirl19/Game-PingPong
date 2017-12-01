package code.core;


public class Presenter<V> {
    private V view;

    protected V view() {
        return view;
    }

    public void bindView(V view) {
        if (this.view != null) {
            throw new IllegalStateException("Presenter already has view!");
        }

        this.view = view;
    }
}
