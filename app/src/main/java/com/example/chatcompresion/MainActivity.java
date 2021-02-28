package com.example.chatcompresion;

import Huffman.Huffman;
import Huffman.HuffmanImagenes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity {

    private CircleImageView fotoPerfil;
    private TextView nombre;
    private RecyclerView rvMensajes;
    private EditText txtMensajes;
    private Button btnEnviar;
    private ImageButton btnEnviarFoto;

    private AdapterMensajes adapter;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private static final int PHOTO_SEND = 1;
    private static final int PHOTO_PERFIL = 2;

    private String fotoPerfilCadena;

    int [][] rojo;
    int [][] verde;
    int [][] azul;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fotoPerfil = (CircleImageView) findViewById(R.id.fotoPerfil);
        nombre = (TextView) findViewById(R.id.nombre);
        rvMensajes = (RecyclerView) findViewById(R.id.rvMensajes);
        txtMensajes = (EditText) findViewById(R.id.txtMensaje);
        btnEnviar = (Button) findViewById(R.id.btnEnviar);
        btnEnviarFoto = (ImageButton) findViewById(R.id.btnEnviarFoto);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("chat_encriptado");

        fotoPerfilCadena = "";

        storage = FirebaseStorage.getInstance();

        adapter = new AdapterMensajes(this);
        LinearLayoutManager l = new LinearLayoutManager(this);
        rvMensajes.setLayoutManager(l);
        rvMensajes.setAdapter(adapter);

        btnEnviar.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                Huffman h = new Huffman();
                h.crearDiccionario(txtMensajes.getText().toString());
                Map<String, Integer> map = new TreeMap<String, Integer>(h.getFreq());
                h.setFreq(map);
                h.contruirArbol();
                h.crearCodigo();

                databaseReference.push().setValue( new MensajeEnviar(h.getCadenaNueva(), nombre.getText().toString(), fotoPerfilCadena, "1", ServerValue.TIMESTAMP, map ));
                txtMensajes.setText("");

            }
        });

        btnEnviarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.setType("image/*");
                i.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(i, "Selecciona una imagen"),PHOTO_SEND);

            }
        });

        fotoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.setType("image/*");
                i.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(i, "Selecciona una imagen"),PHOTO_PERFIL);

            }
        });

        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {

            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                setScrollBar();
            }
        });

        databaseReference.addChildEventListener(new ChildEventListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                MensajeRecibir m = snapshot.getValue(MensajeRecibir.class);

                if(m.getType_mensaje().equals("1")){

                    Huffman h = new Huffman();
                    Map<String, Integer> map = new TreeMap<String, Integer>(m.getFreq1());
                    h.setFreq(map);
                    h.contruirArbol();
                    h.setCadenaNueva(m.getMensaje1() + "1");
                    h.decodificar();
                    m.setMensaje1(h.getCadenaDeco());

                    adapter.addMensaje(m);

                } else if (m.getType_mensaje().equals("2")){

                    HuffmanImagenes h = new HuffmanImagenes();
                    Map<String, Integer> map = new TreeMap<String, Integer>(m.getFreq1());
                    map.remove("a");
                    h.setFreq(map);
                    h.contruirArbol();
                    h.setCadenaNueva(m.getMensaje1() + "1");

                    rojo = h.desencriptar(m.getAlto(), m.getAncho());

                    h=null;

                    HuffmanImagenes h2 = new HuffmanImagenes();
                    Map<String, Integer> map2 = new TreeMap<String, Integer>(m.getFreq2());
                    map2.remove("a");
                    h2.setFreq(map2);
                    h2.contruirArbol();
                    h2.setCadenaNueva(m.getMensaje2() + "1");

                    verde = h2.desencriptar(m.getAlto(), m.getAncho());

                    h2=null;

                    HuffmanImagenes h3 = new HuffmanImagenes();
                    Map<String, Integer> map3 = new TreeMap<String, Integer>(m.getFreq3());
                    map3.remove("a");
                    h3.setFreq(map3);
                    h3.contruirArbol();
                    h3.setCadenaNueva(m.getMensaje3() + "1");

                    azul = h3.desencriptar(m.getAlto(), m.getAncho());

                    h3=null;

                    Bitmap image = Bitmap.createBitmap(m.getAncho(), m.getAlto(), Bitmap.Config.ARGB_8888);

                    for (int y = 0; y < m.getAlto(); y++) {
                        for (int x = 0; x < m.getAncho(); x++) {
                            image.setPixel(x, y, Color.rgb(rojo[y][x], verde[y][x], azul[y][x]));
                        }
                    }

                    m.setBitmap(image);

                    adapter.addMensaje(m);

                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {


            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }


    private void setScrollBar(){

        rvMensajes.scrollToPosition(adapter.getItemCount() - 1);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PHOTO_SEND && resultCode == RESULT_OK) {

            Uri imageUri = data.getData();

            try {

                Bitmap yourBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);

                int proporcion = 100;

                Bitmap bitmap = Bitmap.createScaledBitmap(yourBitmap, proporcion, proporcion, true);

                rojo = new int[proporcion][proporcion];
                verde = new int[proporcion][proporcion];
                azul = new int[proporcion][proporcion];

                for (int i = 0; i < proporcion; i++) {

                    for (int j = 0; j < proporcion; j++) {

                        int pixel = bitmap.getPixel(j, i);
                        capturarRGB(pixel, i, j);

                    }
                }

                HuffmanImagenes hi = new HuffmanImagenes();

                hi.crearDiccionario(rojo, proporcion, proporcion);
                Map<String, Integer> map = new TreeMap<String, Integer>(hi.getFreq());
                hi.setFreq(map);
                hi.contruirArbol();
                hi.crearCodigo();

                HuffmanImagenes hi2 = new HuffmanImagenes();

                hi2.crearDiccionario(verde, proporcion, proporcion);
                Map<String, Integer> map2 = new TreeMap<String, Integer>(hi2.getFreq());
                hi2.setFreq(map2);
                hi2.contruirArbol();
                hi2.crearCodigo();

                HuffmanImagenes hi3 = new HuffmanImagenes();

                hi3.crearDiccionario(azul, proporcion, proporcion);
                Map<String, Integer> map3 = new TreeMap<String, Integer>(hi3.getFreq());
                hi3.setFreq(map3);
                hi3.contruirArbol();
                hi3.crearCodigo();

                Integer a = 3;
                map.put("a", a);
                map2.put("a", a);
                map3.put("a", a);

                databaseReference.push().setValue( new MensajeEnviar(hi.getCadenaNueva(), hi2.getCadenaNueva(), hi3.getCadenaNueva(), nombre.getText().toString(), fotoPerfilCadena, "2", ServerValue.TIMESTAMP, map, map2, map3, proporcion, proporcion ));

                hi=null;
                hi2=null;
                hi3=null;

            } catch (IOException e) {
                e.printStackTrace();
            }


        } else if(requestCode == PHOTO_PERFIL && resultCode == RESULT_OK){

            Uri u = data.getData();
            storageReference = storage.getReference("foto_perfil");
            final StorageReference fotoReferencia = storageReference.child(u.getLastPathSegment());
            fotoReferencia.putFile(u).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    if (taskSnapshot.getMetadata() != null) {
                        if (taskSnapshot.getMetadata().getReference() != null) {
                            Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                            result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    //fotoPerfilCadena = uri.toString();
                                    //MensajeEnviar m = new MensajeEnviar("Kevin te ha actualizado su foto de perfil", uri.toString(), nombre.getText().toString(),fotoPerfilCadena,"2", ServerValue.TIMESTAMP, null);
                                    //databaseReference.push().setValue(m);
                                    //Glide.with(MainActivity.this).load(u.toString()).into(fotoPerfil);
                                }
                            });
                        }
                    }


                }
            });

        }

    }

    public void capturarRGB(int pixel, int j, int i) {

        int red = (pixel >> 16) & 0xff;
        rojo[j][i] = red;
        int green = (pixel >> 8) & 0xff;
        verde[j][i] = green;
        int blue = (pixel) & 0xff;
        azul[j][i] = blue;

    }

}