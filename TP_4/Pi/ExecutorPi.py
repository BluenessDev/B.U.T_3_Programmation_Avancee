import matplotlib.pyplot as plt
import pandas as pd
import os
import subprocess
import glob
import platform
import calcul_courbe

def run_java_program(num_throws, num_workers, name_file, mode):
    # Récupérer tous les fichiers Java dans le dossier actuel
    java_files = glob.glob("*.java")  # Liste tous les fichiers .java dans le dossier
    if not java_files:
        print("Erreur : Aucun fichier Java trouvé pour la compilation.")
        return None

    # Compiler les fichiers Java
    compile_command = ["javac", "-d", "."] + java_files
    print("Compilation des fichiers Java :", " ".join(java_files))
    
    try:
        subprocess.run(compile_command, check=True)
        print("Compilation réussie.")
    except subprocess.CalledProcessError as e:
        print("Erreur lors de la compilation :", e)
        return None

    class_name = "TP_4.Pi.Pi"

    results = []

    # Exécuter le programme Java 15 fois
    for i in range(15):
        run_command = ["java", class_name, str(num_throws), str(num_workers), name_file]
        print(f"Exécution du programme Java ({mode}, tentative {i + 1}, workers : {num_workers})...")
        try:
            result = subprocess.run(run_command, capture_output=True, text=True, check=True)
            print("Sortie :")
            print(result.stdout)
            results.append(result.stdout.strip())
        except subprocess.CalledProcessError as e:
            print("Erreur lors de l'exécution :", e)
            if e.stdout:
                print("Sortie standard :")
                print(e.stdout)
            if e.stderr:
                print("Sortie des erreurs :")
                print(e.stderr)
    return results
    

# --- Main ---
# Recuperer le nom de l'ordinateur
computer_name = platform.node()
print(f"Nom de l'ordinateur : {computer_name}")

filenameFaible = computer_name + "_Pi_faible"
filenameForte = computer_name + "_Pi_forte"

# Variables
nbLancer = 100_000_000
maxProcess = 8

# Renvoie le dossier actuel
current_dir = os.getcwd()
print(f"Dossier actuel : {current_dir}")

# Retirer les fichiers CSV existants
for filename in [filenameFaible, filenameForte]:
    for ext in ["csv", "png"]:
        file = f"{filename}.{ext}"
        if os.path.exists(file):
            os.remove(file)
            print(f"Fichier {file} supprimé.")

for nbProcess in range(1, maxProcess + 1):
    print(f"\n=== Test Scalabilité faible avec {nbProcess} process ===")
    run_java_program(int(nbLancer * nbProcess), nbProcess, filenameFaible, "Faible")

for nbProcess in range(1, maxProcess + 1):
    print(f"\n=== Test Scalabilité forte avec {nbProcess} process ===")
    run_java_program(int(nbLancer), nbProcess, filenameForte, "Forte")