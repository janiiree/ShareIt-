package Adapters;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shareit.R;
import com.example.shareit.objetos.LostOBJ;

import java.util.List;




public class AdapterLO extends BaseAdapter {

    private List<LostOBJ> objetos;
    Context contexto;
    LayoutInflater inflater;
    public AdapterLO(Context contexto, List<LostOBJ> obj) {
        this.contexto = contexto;
        this.inflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.objetos = obj;
    }

    @Override
    public int getCount() {
        return objetos.size();
    }

    @Override
    public LostOBJ getItem(int i) {
        return objetos.get(i);
    }



    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view=inflater.inflate(R.layout.LOItem ,null);
        TextView tvNombre= (TextView) view.findViewById(R.id.nombreObj);
        TextView tvDesc=(TextView) view.findViewById(R.id.descObj;
        TextView tvCorreo= (TextView) view.findViewById(R.id.correo);
        ImageView ivFoto= (ImageView) view.findViewById(R.id.idImagen);
        tvNombre.setText(objetos.get(i).getNombreLO();
        tvCorreo.setText(objetos.get(i).getCorreoLO();
        tvDesc.setText(objetos.get(i).getDescLO();
        ivFoto.setImageBitmap(objetos.get(i).getImagenLO());
        return view;
    }
}