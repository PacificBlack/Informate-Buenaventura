package com.pacificblack.informatebuenaventura.bienvenida;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.pacificblack.informatebuenaventura.R;

import java.util.List;

public class InicioViewpagerAdaptador extends PagerAdapter {

    Context context;
    List<ItemInicio> itemInicioList;
    View layoutScreen;
    LayoutInflater inflater;

    public InicioViewpagerAdaptador(Context context, List<ItemInicio> itemInicioList) {
        this.context = context;
        this.itemInicioList = itemInicioList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

         inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

         layoutScreen = inflater.inflate(R.layout.layout_screen,null);

        ImageView imgSlide = layoutScreen.findViewById(R.id.intro_img);
        TextView title = layoutScreen.findViewById(R.id.intro_title);
        TextView description = layoutScreen.findViewById(R.id.intro_description);

        title.setText(itemInicioList.get(position).getTituli());
        description.setText(itemInicioList.get(position).getDescripcion());
        imgSlide.setImageResource(itemInicioList.get(position).getImagen());

        container.addView(layoutScreen);

        return layoutScreen;
    }

    @Override
    public int getCount() {
        return itemInicioList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
