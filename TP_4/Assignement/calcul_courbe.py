import matplotlib.pyplot as plt
import pandas as pd
import os
import platform
import glob

def courbeFaibleScalabilite(filename):
    # On récupère les fichiers de données
    files = glob.glob(filename)
    if not files:
        print(f"Erreur : Aucun fichier trouvé pour {filename}")
        return

    # On crée un DataFrame vide
    df = pd.DataFrame()

    # On parcourt les fichiers
    for file in files:
        try:
            # On lit le fichier avec le délimiteur ';'
            df_temp = pd.read_csv(file, sep=";", encoding="utf-8")
            df_temp.columns = df_temp.columns.str.strip()  # Supprime les espaces
            df = pd.concat([df, df_temp], ignore_index=True)
        except Exception as e:
            print(f"Erreur de lecture du fichier {file}: {e}")
            return

    # Vérification des colonnes
    print("Colonnes disponibles :", df.columns)
    if "numWorkers" not in df.columns or "Duree" not in df.columns:
        print("Erreur : Colonnes 'numWorkers' ou 'Duree' manquantes.")
        return

    # Calcul du speedup
    df_single_worker = df[df["numWorkers"] == 1]
    if df_single_worker.empty:
        print("Erreur : Aucune donnée avec numWorkers = 1.")
        return

    single_worker_duration = df_single_worker["Duree"].mean()
    df["speedup"] = single_worker_duration / df["Duree"]

    # Calcul de la médiane des speedup par nombre de travailleurs
    df_median = df.groupby("numWorkers")["speedup"].median().reset_index()

    # On affiche la courbe
    plt.figure(figsize=(8, 5))
    plt.plot(df_median["numWorkers"], df_median["speedup"], marker='o', linestyle='-', label="Speedup (médiane)")
    plt.plot(df_median["numWorkers"], [1] * len(df_median["numWorkers"]), color="red", label="Scalabilité optimale (y=1)")
    plt.xlabel("Nombre de travailleurs")
    plt.ylabel("Speedup")
    plt.title("Faible scalabilité de l'ordinateur " + computer_name)
    plt.legend()
    plt.grid()
    plt.savefig("faible_scalabilite_plot.png")
    plt.show()


def courbeForteScalabilite(filename):
    files = glob.glob(filename)
    if not files:
        print(f"Erreur : Aucun fichier trouvé pour {filename}")
        return

    df = pd.DataFrame()
    for file in files:
        try:
            # On lit le fichier avec le délimiteur ';'
            df_temp = pd.read_csv(file, sep=";", encoding="utf-8")
            df_temp.columns = df_temp.columns.str.strip()
            df = pd.concat([df, df_temp], ignore_index=True)
        except Exception as e:
            print(f"Erreur de lecture du fichier {file}: {e}")
            return

    if "numWorkers" not in df.columns or "Duree" not in df.columns:
        print("Erreur : Colonnes 'numWorkers' ou 'Duree' manquantes.")
        return

    df_single_worker = df[df["numWorkers"] == 1]
    if df_single_worker.empty:
        print("Erreur : Aucune donnée avec numWorkers = 1.")
        return

    single_worker_duration = df_single_worker["Duree"].mean()
    df["speedup"] = single_worker_duration / df["Duree"]
    df_median = df.groupby("numWorkers")["speedup"].median().reset_index()

    # Scalabilité optimale y = x
    n_workers = df_median["numWorkers"]
    speedup_ideal = n_workers  # Scalabilité idéale linéaire

    plt.figure(figsize=(8, 5))
    plt.plot(n_workers, df_median["speedup"], marker='o', linestyle='-', label="Speedup (médiane)")
    plt.plot(n_workers, speedup_ideal, color="red", label="Scalabilité optimale (y=x)")
    plt.xlabel("Nombre de travailleurs")
    plt.ylabel("Speedup")
    plt.title("Forte scalabilité de l'ordinateur " + computer_name)
    plt.legend()
    plt.grid()
    plt.savefig("forte_scalabilite_plot.png")
    plt.show()

def nuageDePointsTauxErreur(filename):
    files = glob.glob(filename)
    if not files:
        print(f"Erreur : Aucun fichier trouvé pour {filename}")
        return

    df = pd.DataFrame()
    for file in files:
        try:
            # On lit le fichier avec le délimiteur ';'
            df_temp = pd.read_csv(file, sep=";", encoding="utf-8")
            df_temp.columns = df_temp.columns.str.strip()
            df = pd.concat([df, df_temp], ignore_index=True)
        except Exception as e:
            print(f"Erreur de lecture du fichier {file}: {e}")
            return

    if "totalIterations" not in df.columns or "Error" not in df.columns:
        print("Erreur : Colonnes 'totalIterations' ou 'Error' manquantes.")
        return

    df_median = df.groupby("totalIterations")["Error"].median().reset_index()

    plt.figure(figsize=(8, 5))
    plt.xscale("log")
    plt.yscale("log")
    plt.scatter(df["totalIterations"], df["Error"], alpha=0.5, label="Erreur vs nombre de points")
    plt.scatter(df_median["totalIterations"], df_median["Error"], color="red", s=100, label="Médian par Npoint")
    plt.xlabel("Nombre de points")
    plt.ylabel("Erreur")
    plt.title("Taux d'erreur en fonction du nombre de points")
    plt.legend()
    plt.grid()
    plt.savefig("taux_erreur_plot.png")
    plt.show()

os.chdir("Assignement")

# Recuperer le nom de l'ordinateur
computer_name = platform.node()

filenameFaible = computer_name + "_Montecarlo_faible.csv"
filenameForte = computer_name + "_Montecarlo_forte.csv"

courbeFaibleScalabilite(filenameFaible)
courbeForteScalabilite(filenameForte)
nuageDePointsTauxErreur(filenameFaible)
