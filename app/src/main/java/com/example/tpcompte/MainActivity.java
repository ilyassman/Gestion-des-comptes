package com.example.tpcompte;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tpcompte.adapter.CompteAdapter;
import com.example.tpcompte.beans.Compte;
import com.example.tpcompte.repository.CompteRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements CompteAdapter.OnDeleteClickListener,CompteAdapter.OnUpdateClickListener {
    private RecyclerView recyclerView;
    private CompteAdapter adapter;
    private RadioGroup formatGroup;
    private FloatingActionButton addbtn;
    @Override
    public void onUpdateClick(Compte compte) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        // Inflater la vue du dialogue
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_compte, null);

        // Récupérer les références des vues
        EditText etSolde = dialogView.findViewById(R.id.etSolde);
        RadioGroup typeGroup = dialogView.findViewById(R.id.typeGroup);
        etSolde.setText(String.valueOf(compte.getSolde()));
        if (compte.getType().equalsIgnoreCase("COURANT")) {
            typeGroup.check(R.id.radioCourant);
        } else if (compte.getType().equalsIgnoreCase("EPARGNE")) {
            typeGroup.check(R.id.radioEpargne);
        }
        // Configurer le dialogue
        builder.setView(dialogView)
                .setTitle("Modifier un compte")
                .setPositiveButton("Modifier", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Récupérer les valeurs
                        String solde = etSolde.getText().toString();
                        String type = typeGroup.getCheckedRadioButtonId() == R.id.radioCourant
                                ? "COURANT" : "EPARGNE";
                        compte.setSolde(Double.parseDouble(solde));
                        compte.setType(type);
                        CompteRepository compteRepository=new CompteRepository("JSON");
                        compteRepository.updateCompte(compte.getId(), compte, new Callback<Compte>() {
                            @Override
                            public void onResponse(Call<Compte> call, Response<Compte> response) {
                                if(response.isSuccessful())
                                    Toast.makeText(MainActivity.this,
                                            "Compte Modifie avec succès",
                                            Toast.LENGTH_LONG).show();
                                loadData("JSON");
                            }

                            @Override
                            public void onFailure(Call<Compte> call, Throwable t) {

                            }
                        });





                    }
                })
                .setNegativeButton("Annuler", null);

        // Afficher le dialogue
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    @Override
    public void onDeleteClick(Compte compte) {
        // Afficher une boîte de dialogue de confirmation avant la suppression
        new AlertDialog.Builder(this)
                .setTitle("Confirmation")
                .setMessage("Voulez-vous vraiment supprimer ce compte ?")
                .setPositiveButton("Oui", (dialog, which) -> {
                    CompteRepository compteRepository = new CompteRepository("JSON");
                    compteRepository.deleteCompte(compte.getId(), new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(MainActivity.this,
                                        "Compte supprimé avec succès",
                                        Toast.LENGTH_LONG).show();
                                loadData("JSON"); // Recharger les données
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(MainActivity.this,
                                    "Erreur lors de la suppression",
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                })
                .setNegativeButton("Non", null)
                .show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        recyclerView = findViewById(R.id.recyclerView);
        formatGroup = findViewById(R.id.formatGroup);
        addbtn=findViewById(R.id.fabAdd);

        // Setup RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CompteAdapter(MainActivity.this,MainActivity.this);
        recyclerView.setAdapter(adapter);
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                // Inflater la vue du dialogue
                View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_compte, null);

                // Récupérer les références des vues
                EditText etSolde = dialogView.findViewById(R.id.etSolde);
                RadioGroup typeGroup = dialogView.findViewById(R.id.typeGroup);

                // Configurer le dialogue
                builder.setView(dialogView)
                        .setTitle("Ajouter un compte")
                        .setPositiveButton("Ajouter", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Récupérer les valeurs
                                String solde = etSolde.getText().toString();
                                String type = typeGroup.getCheckedRadioButtonId() == R.id.radioCourant
                                        ? "COURANT" : "EPARGNE";

                                // Afficher le toast

                                Calendar calendar = Calendar.getInstance();

                                // Définir un format pour la date
                                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

                                // Convertir la date en String
                                String formattedDate = formatter.format(calendar.getTime());
                                Compte compte=new Compte(null,Double.parseDouble(solde),type,formattedDate);
                                CompteRepository compteRepository=new CompteRepository("JSON");
                                compteRepository.addCompte(compte, new Callback<Compte>() {
                                    @Override
                                    public void onResponse(Call<Compte> call, Response<Compte> response) {
                                        if(response.isSuccessful()) {
                                            Toast.makeText(MainActivity.this,
                                                    "Compte Ajoutee",
                                                    Toast.LENGTH_LONG).show();
                                            loadData("JSON");
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Compte> call, Throwable t) {

                                    }
                                });

                            }
                        })
                        .setNegativeButton("Annuler", null);

                // Afficher le dialogue
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        // Setup format selection
        formatGroup.setOnCheckedChangeListener((group, checkedId) -> {
            String format = checkedId == R.id.radioJson ? "JSON" : "XML";
            loadData(format);
        });

        // Initial load
        loadData("JSON");
    }

    private void loadData(String format) {
        CompteRepository compteRepository = new CompteRepository(format);
        compteRepository.getAllCompte(new Callback<List<Compte>>() {
            @Override
            public void onResponse(Call<List<Compte>> call, Response<List<Compte>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Compte> comptes=response.body();
                    for(Compte compte:comptes)
                        Log.d("Compte data "+format,compte.toString());
                    runOnUiThread(() -> {
                        adapter.updateData(response.body());
                    });
                }
            }

            @Override
            public void onFailure(Call<List<Compte>> call, Throwable t) {
                runOnUiThread(() -> {
                    Toast.makeText(MainActivity.this,
                            "Erreur: " + t.getMessage(),
                            Toast.LENGTH_LONG).show();
                });
            }
        });
    }


}