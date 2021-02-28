package com.example.chatcompresion;

import Huffman.Encriptado;
import Huffman.Huffman;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
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

import java.nio.ByteBuffer;
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

                System.out.println("Enviar");

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
                i.setType("image/jpeg");
                i.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(i, "Selecciona una imagen"),PHOTO_SEND);

            }
        });

        fotoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.setType("image/jpeg");
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

                System.out.println("Recibir");

                MensajeRecibir m = snapshot.getValue(MensajeRecibir.class);
                Huffman h = new Huffman();
                Map<String, Integer> map = new TreeMap<String, Integer>(m.getFreq());
                h.setFreq(map);
                h.contruirArbol();
                h.setCadenaNueva(m.getMensaje() + "1");
                h.decodificar();
                m.setMensaje(h.getCadenaDeco());

                adapter.addMensaje(m);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PHOTO_SEND && resultCode == RESULT_OK){

            Uri u = data.getData();
            storageReference = storage.getReference("imagenes_chat");
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

                                    //Image image = reader.acquireLatestImage();
                                    //ByteBuffer buffer = image.getPlanes()[0].getBuffer();
                                    //byte[] bytes = new byte[buffer.capacity()];
                                    //buffer.get(bytes);
                                    //Bitmap bitmapImage = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, null);

                                    MensajeEnviar m = new MensajeEnviar("Kevin te ha enviado una foto", uri.toString(), nombre.getText().toString(),fotoPerfilCadena,"2", ServerValue.TIMESTAMP, null);
                                    databaseReference.push().setValue(m);
                                }
                            });
                        }
                    }


                }
            });

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
                                    fotoPerfilCadena = uri.toString();
                                    MensajeEnviar m = new MensajeEnviar("Kevin te ha actualizado su foto de perfil", uri.toString(), nombre.getText().toString(),fotoPerfilCadena,"2", ServerValue.TIMESTAMP, null);
                                    databaseReference.push().setValue(m);
                                    Glide.with(MainActivity.this).load(u.toString()).into(fotoPerfil);
                                }
                            });
                        }
                    }


                }
            });

        }

    }
}