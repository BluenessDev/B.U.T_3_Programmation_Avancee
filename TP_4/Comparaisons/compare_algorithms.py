import matplotlib.pyplot as plt
import pandas as pd
import glob

def read_csv_files(pattern):
    files = glob.glob(pattern)
    if not files:
        print(f"Erreur : Aucun fichier trouvé pour {pattern}")
        return pd.DataFrame()

    df = pd.DataFrame()
    for file in files:
        try:
            df_temp = pd.read_csv(file, sep=";", encoding="utf-8")
            df_temp.columns = df_temp.columns.str.strip()
            df = pd.concat([df, df_temp], ignore_index=True)
        except Exception as e:
            print(f"Erreur de lecture du fichier {file}: {e}")
    return df

def plot_strong_scalability():
    plt.close("all")  # Nettoie les anciennes figures

    patterns_forte = [
        "./Comparaisons/MacBook-Pro-de-Cyril.local_Socket_forte_*.csv",
        "./Comparaisons/MacBook-Pro-de-Cyril.local_Pi_forte_*.csv",
        "./Comparaisons/MacBook-Pro-de-Cyril.local_Montecarlo_forte_*.csv"
    ]
    labels_forte = ["Socket Forte", "Pi Forte", "Montecarlo Forte"]
    colors = ["blue", "green", "orange"]

    num_points_list = [10_000_000, 100_000_000]
    fig, axs = plt.subplots(1, 2, figsize=(18, 6))

    for i, num_points in enumerate(num_points_list):
        has_data = False

        for pattern, label, color in zip(patterns_forte, labels_forte, colors):
            df = read_csv_files(pattern)
            if df.empty:
                continue

            df_filtered = df[df["totalIterations"] == num_points].copy()
            if df_filtered.empty:
                print(f"Erreur : Aucune donnée pour {num_points} points pour {label}.")
                continue

            df_single_worker = df_filtered[df_filtered["numWorkers"] == 1]
            if df_single_worker.empty:
                print(f"Erreur : Aucune donnée avec numWorkers = 1 pour {label}.")
                continue

            single_worker_duration = df_single_worker["Duree"].median()
            df_filtered["speedup"] = single_worker_duration / df_filtered["Duree"]
            df_median = df_filtered.groupby("numWorkers")["speedup"].median().reset_index()

            axs[i].plot(df_median["numWorkers"], df_median["speedup"], marker='o', linestyle='-', color=color, label=label)
            has_data = True

        if not has_data:
            continue

        axs[i].plot(df_median["numWorkers"], df_median["numWorkers"], color="red", linestyle='--', label="Scalabilité optimale (y=x)")
        axs[i].set_xlabel("Nombre de travailleurs")
        axs[i].set_ylabel("Speedup")
        axs[i].set_title(f"Forte scalabilité pour {num_points} points")
        axs[i].legend()
        axs[i].grid()

    plt.tight_layout()
    plt.savefig("comparaison_algorithmes_forte.png")
    plt.show()

def plot_weak_scalability():
    plt.close("all")

    patterns_faible = [
        "./Comparaisons/MacBook-Pro-de-Cyril.local_Socket_faible_*.csv",
        "./Comparaisons/MacBook-Pro-de-Cyril.local_Pi_faible_*.csv",
        "./Comparaisons/MacBook-Pro-de-Cyril.local_Montecarlo_faible_*.csv"
    ]
    labels_faible = ["Socket Faible", "Pi Faible", "Montecarlo Faible"]
    colors = ["blue", "green", "orange"]

    # Ici, deux sous-plots selon la valeur de base attendue (ex: 10M ou 100M pour 1 travailleur)
    num_points_list = [10_000_000, 100_000_000]
    fig, axs = plt.subplots(1, 2, figsize=(18, 6))

    for i, base_points in enumerate(num_points_list):
        has_data = False

        for pattern, label, color in zip(patterns_faible, labels_faible, colors):
            df = read_csv_files(pattern)
            if df.empty:
                continue

            # Pour weak scalability, on ne retient que les lignes où
            # totalIterations == base_points * numWorkers.
            df_filtered = df[df.apply(lambda row: row["totalIterations"] == base_points * row["numWorkers"], axis=1)].copy()
            if df_filtered.empty:
                print(f"Erreur : Aucune donnée pour base {base_points} pour {label}.")
                continue

            df_single_worker = df_filtered[df_filtered["numWorkers"] == 1]
            if df_single_worker.empty:
                print(f"Erreur : Aucune donnée avec numWorkers = 1 pour {label}.")
                continue

            baseline_duration = df_single_worker["Duree"].median()
            df_filtered["speedup"] = baseline_duration / df_filtered["Duree"]
            df_median = df_filtered.groupby("numWorkers")["speedup"].median().reset_index()

            axs[i].plot(df_median["numWorkers"], df_median["speedup"],marker='o', linestyle='-', color=color, label=label)
            has_data = True

        if not has_data:
            continue

        axs[i].plot(df_median["numWorkers"], [1] * len(df_median["numWorkers"]),
                    color="red", linestyle='--', label="Scalabilité optimale (y=1)")
        axs[i].set_xlabel("Nombre de travailleurs")
        axs[i].set_ylabel("Speedup")
        axs[i].set_title(f"Faible scalabilité pour {base_points} points")
        axs[i].legend()
        axs[i].grid()

    plt.tight_layout()
    plt.savefig("comparaison_algorithmes_faible.png")
    plt.show()

plot_strong_scalability()
plot_weak_scalability()