package dime.android.apkcv.ui.views.buble;

/**
 * Created by dime on 26/08/15.
 */
public interface ViewAnimationListener {
    /**
     * Called from a child of the view, when the parent view needs to redraw.
     *
     * @param bubbleOnTop
     *          The given bubble will be last on the draw queue - and on top of everything else.
     */
    void invalidateView(ColorBubble bubbleOnTop);
    /**
     * Called when the tap animation finishes.
     *
     * @param bubble
     */
    void tapAnimationFinished(ColorBubble bubble);
}
