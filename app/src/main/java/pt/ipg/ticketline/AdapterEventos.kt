package pt.ipg.ticketline

import android.database.Cursor
import android.provider.BaseColumns
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdapterEventos(val fragment: ListaEventosFragment) : RecyclerView.Adapter<AdapterEventos.ViewHolderEvento>() {
    var cursor: Cursor? = null
        get() = field
        set(value) {
            if (field != value) {
                field = value
                notifyDataSetChanged()
            }
        }

    class ViewHolderEvento(itemEvento: View) : RecyclerView.ViewHolder(itemEvento) {
        val textViewNome = itemEvento.findViewById<TextView>(R.id.textViewNome)
        val textViewData = itemEvento.findViewById<TextView>(R.id.textViewData)
        val textViewLocal = itemEvento.findViewById<TextView>(R.id.textViewLocal)

        var evento : ClassEvento? = null
            get() = field
            set(value: ClassEvento?) {
                field = value

                textViewNome.text = evento?.nome_evento ?: ""
                textViewData.text = (evento?.data ?: "") as CharSequence?
                textViewLocal.text = "${evento?.local_id}"
            }
    }

    /**
     * Called when RecyclerView needs a new [ViewHolder] of the given type to represent
     * an item.
     *
     *
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     *
     *
     * The new ViewHolder will be used to display items of the adapter using
     * [.onBindViewHolder]. Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary [View.findViewById] calls.
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     * an adapter position.
     * @param viewType The view type of the new View.
     *
     * @return A new ViewHolder that holds a View of the given view type.
     * @see .getItemViewType
     * @see .onBindViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderEvento {
        val itemEvento = fragment.layoutInflater.inflate(R.layout.item_evento, parent, false)
        return ViewHolderEvento(itemEvento)
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the [ViewHolder.itemView] to reflect the item at the given
     * position.
     *
     *
     * Note that unlike [android.widget.ListView], RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the `position` parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use [ViewHolder.getAdapterPosition] which will
     * have the updated adapter position.
     *
     * Override [.onBindViewHolder] instead if Adapter can
     * handle efficient partial bind.
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the
     * item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    override fun onBindViewHolder(holder: ViewHolderEvento, position: Int) {
        cursor!!.moveToPosition(position)
        holder.evento = ClassEvento.fromCursor(cursor!!)
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    override fun getItemCount(): Int {
        if (cursor == null) return 0

        return cursor!!.count
    }
}