package es.unizar.eina.T223_comidas.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Database(entities = {Plato.class, Pedido.class, Cantidad.class}, version = 1, exportSchema = false)
public abstract class ComidasRoomDatabase extends RoomDatabase {

    public abstract PlatoDao platoDao();
    public abstract PedidoDao pedidoDao();
    public abstract CantidadDao cantidadDao();

    private static volatile ComidasRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static ComidasRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ComidasRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    ComidasRoomDatabase.class, "comidas_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more notes, just add them.
                PlatoDao daoPlato = INSTANCE.platoDao();
                daoPlato.deleteAll();

                Plato plato = new Plato("1's nombre", "1's descripcion",
                        "SEGUNDO", 1);
                daoPlato.insert(plato);
                plato = new Plato("2's nombre", "2's descripcion",
                        "PRIMERO", 2);
                daoPlato.insert(plato);
            });
        }
    };

}
