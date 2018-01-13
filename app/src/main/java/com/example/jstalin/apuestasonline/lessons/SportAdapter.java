package com.example.jstalin.apuestasonline.lessons;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jstalin.apuestasonline.Interfaces.OnSelectSportListener;
import com.example.jstalin.apuestasonline.R;

import java.util.ArrayList;

/**
 * Created by JStalin on 12/01/2018.
 */

/**
 * Clase que es el adapartador del Recycler View
 */
public class SportAdapter extends RecyclerView.Adapter<SportAdapter.SportViewHolder>  {

    // Contiene los deportes que contendra
    private ArrayList<Sport> sports;

    /**
     * Contructor que permite asignar los items que contendra el adaptador
     * @param sports
     */
    public SportAdapter(ArrayList<Sport> sports){
        this.sports = sports;
    }


    /**
     * Metodo que permite crear un view Holder
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public SportViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Creamos un view que contiene el xml del card view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_sport, parent, false);

        // Creamos un view holder con la view
        SportViewHolder sportViewHolder = new SportViewHolder(view);

        // La devolvemos
        return sportViewHolder;
    }

    /**
     * Metodo que asigna los datos para cada uno de los items
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(SportViewHolder holder, int position) {

        holder.nameSport.setText(sports.get(position).getName());
        holder.imageSport.setImageResource(sports.get(position).getImage());

    }

    /**
     * Metodo que devuelve el total de elementos del recycler
     * @return
     */
    @Override
    public int getItemCount() {
        return sports.size();
    }


    /**
     * Clase que Contiene los datos de cada item y hace la funcion del view holder
     */
    public static class SportViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        // Datos
        private TextView nameSport;
        private ImageView imageSport;
        private CardView cardSport;

        // Evento al pulsar en un item
        private static OnSelectSportListener listenerOnSelectedSport;

        /**
         * Constructor de clase
         * @param itemView
         */
        public SportViewHolder(View itemView) {
            super(itemView);

            // Asignamos el evento
            itemView.setOnClickListener(this);

            // Obtenemos los componentes que compone la cardview
            nameSport = (TextView) itemView.findViewById(R.id.nameSport);
            imageSport = (ImageView) itemView.findViewById(R.id.imageSport);
            cardSport = (CardView) itemView;
        }

        /**
         * Metodo qe permite asignar el evento
         * @param listener
         */
        public static void setOnSelectSportListener(OnSelectSportListener listener){
            listenerOnSelectedSport = listener;
        }


        /**
         * Sobrescripcion del meto onClick , los que hace es volver a lanzar un evneto
         * que contiene el codigo de deporte
         * @param view
         */
        @Override
        public void onClick(View view){
            if(listenerOnSelectedSport != null){
                int codeSport = generateSportCodeOfPosition(getAdapterPosition());
                listenerOnSelectedSport.onSelectedSport(view, codeSport);
            }
        }

        /**
         * Metodo que seguna el objeto seleccionado (su posicion) devuelva el codigo
         * correspondiente de la clase Sport
         * @param position
         * @return
         */
        private int generateSportCodeOfPosition(int position){

            int codeSport = -1;

            switch (position){
                case 0:
                    codeSport = Sport.CODE_FOOTBALL;
                    break;
                case 1:
                    codeSport = Sport.CODE_BASKETBALL;
                    break;
                case 2:
                    codeSport = Sport.CODE_TENNIS;
                    break;
                case 3:
                    codeSport = Sport.CODE_HANDBALL;
                    break;
            }

            return codeSport;
        }
    }

}
