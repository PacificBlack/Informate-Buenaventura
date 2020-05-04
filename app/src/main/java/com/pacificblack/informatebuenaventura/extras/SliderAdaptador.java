package com.pacificblack.informatebuenaventura.extras;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.pacificblack.informatebuenaventura.R;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SliderAdaptador extends SliderViewAdapter<SliderAdaptadorVH> {

    private Context context;
    private List<Imagenes> mSliderItems = new ArrayList<>();

    public SliderAdaptador(Context context, List<Imagenes> listaImagenes) {
        this.context = context;
        this.mSliderItems = listaImagenes;
    }

    @Override
    public SliderAdaptadorVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item, null);
        return new SliderAdaptadorVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdaptadorVH viewHolder, int position) {
        if (mSliderItems.get(position).getImagenes() != null){

            Picasso.get().load(mSliderItems.get(position).getImagenes())
                    .placeholder(R.drawable.imagennodisponible)
                    .error(R.drawable.imagennodisponible)
                    .into(viewHolder.rowimagen);

            final String imagen1_link = mSliderItems.get(position).getImagenes();
            viewHolder.rowimagen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentultima = new Intent(context, FullImagen.class);
                    Bundle envioimg = new Bundle();
                    envioimg.putString("imagen", imagen1_link);
                    intentultima.putExtras(envioimg);
                    context.startActivity(intentultima);
                }
            });


        }else{
            viewHolder.rowimagen.setImageResource(R.drawable.imagennodisponible);

        }
    }

    @Override
    public int getCount() {
        return mSliderItems.size();
    }
}
class SliderAdaptadorVH extends SliderViewAdapter.ViewHolder {
    ImageView rowimagen;

    public SliderAdaptadorVH(View itemView) {
        super(itemView);
        rowimagen = itemView.findViewById(R.id.image_row_slider);

    }
}
