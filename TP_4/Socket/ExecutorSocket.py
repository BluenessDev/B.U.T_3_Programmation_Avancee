import matplotlib.pyplot as plt
import pandas as pd
import os
import subprocess
import glob
import platform
import calcul_courbe
import time

# Lancer le programme Java
master_class_name = "TP_4.Socket.MasterSocket"
worker_class_name = "TP_4.Socket.WorkerSocket"

# Liste des ports utilisés par les workers
WORKER_PORTS = [25545, 25546, 25547, 25548, 25549, 25550, 25551, 25552]

def compile_java_files():
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
    
    return java_files

def launch_workers_for(num_workers):
    """
    Lance num_workers instances de assignments.WorkerSocket.
    Chaque worker est lancé sur un port prédéfini.
    """
    workers = []
    for i in range(num_workers):
        compile_java_files()
        port = WORKER_PORTS[i]
        cmd = ["java", worker_class_name, str(port)]
        print(f"Lancement du worker sur le port {port} : {' '.join(cmd)}")
        proc = subprocess.Popen(cmd, stdout=subprocess.DEVNULL, stderr=subprocess.DEVNULL)
        workers.append(proc)
    time.sleep(1)
    return workers

def run_master_socket(iterations, p, mode):
    """
    Lance p workers, exécute assignments.MasterSocket avec les paramètres donnés,
    puis termine les workers.
    """
    compile_java_files()
    workers = launch_workers_for(p)
    cmd = ["java", master_class_name, str(iterations), str(p), mode]
    print("Exécution :", cmd)
    subprocess.run(cmd, stdout=subprocess.DEVNULL, stderr=subprocess.DEVNULL)
    for proc in workers:
        proc.kill()
    time.sleep(0.5)

def run_java_program(num_throws, num_workers, name_file, mode):
    """
    Lance le programme Java avec le nombre de lancers et de travailleurs donnés.
    """
    results = []
    for i in range(15):
        run_master_socket(num_throws, num_workers, mode)
    return results

# --- Main ---

# Recuperer le nom de l'ordinateur
computer_name = platform.node()
print(f"Nom de l'ordinateur : {computer_name}")

filenameFaible = computer_name + "_Socket_faible"
filenameForte = computer_name + "_Socket_forte"

# Variables
nbLancer = 1_000_000
maxProcess = 8

# Renvoie le dossier actuel
current_dir = os.getcwd()
print(f"Dossier actuel : {current_dir}")

# Se déplacer dans le dossier Java
java_dir = os.path.join(current_dir, "Socket")
os.chdir(java_dir)

for nbProcess in range(1, maxProcess + 1):
    print(f"\n=== Test Scalabilité faible avec {nbProcess} instances ===")
    run_java_program(int(nbLancer * nbProcess), nbProcess, "Faible")

for nbProcess in range(1, maxProcess + 1):
    print(f"\n=== Test Scalabilité forte avec {nbProcess} instances ===")
    run_java_program(int(nbLancer), nbProcess, "Forte")

CSVFort = computer_name + "_Socket_forte*.csv"
CSVFaible = computer_name + "_Socket_faible*.csv"

calcul_courbe.courbeFaibleScalabilite(CSVFaible)
calcul_courbe.courbeForteScalabilite(CSVFort)
