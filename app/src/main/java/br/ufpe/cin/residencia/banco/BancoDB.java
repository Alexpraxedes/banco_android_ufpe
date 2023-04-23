package br.ufpe.cin.residencia.banco;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import br.ufpe.cin.residencia.banco.conta.Conta;
import br.ufpe.cin.residencia.banco.conta.ContaDAO;

//ESTA CLASSE NAO PRECISA SER MODIFICADA, SE NAO FOR IMPLEMENTAR A FUNCIONALIDADE DE CLIENTES!
@Database(entities = {Conta.class}, version = 1)
public abstract class BancoDB extends RoomDatabase {
    public abstract ContaDAO contaDAO();

    public static final String DB_NAME = "banco.db";
    private static volatile BancoDB INSTANCE;
    public synchronized static BancoDB getDB(Context c) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                    c,
                    BancoDB.class,
                    DB_NAME
            ).build();
        }
        return INSTANCE;
    }
}
