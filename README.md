<div align="center">
  <h1>Programmation Avancée BUT Informatique 2024-2025</h1>
  <a href="https://fr.wikipedia.org/wiki/Java_(langage)"><img src="https://img.shields.io/badge/Java-red?style=for-the-badge&logo=visualstudiocode"/></a>
</div>

## À propos

Ce dépôt regroupe les rapports et codes de mes travaux pratiques en programmation avancée Java, focalisés sur les concepts de **programmation parallèle**, **gestion de threads** et **synchronisation**. Ces TP explorent des concepts essentiels comme les **threads**, les **sémaphores** et les **sections critiques**, avec des exemples concrets et des applications graphiques en Java.

## Table des TP

### [TP 1 : Programmation parallèle et Threads avec des mobiles](./TP_1/RapportTP1.md)
- **Objectif** : Découverte de la programmation parallèle avec Java, en utilisant des threads pour contrôler des objets mobiles dans une interface graphique.
- **Concepts abordés** :
    - Création et gestion de threads
    - Utilisation de `sleep()` pour gérer la vitesse des objets mobiles
    - Contrôle de l'animation avec des boutons interactifs pour démarrer et arrêter les threads
- **Résumé** : Dans ce TP, un objet mobile se déplace dans une interface graphique en utilisant un thread Java. Le mouvement peut être contrôlé par l’utilisateur à l’aide d’un bouton qui met en pause et reprend l’animation.

### [TP 2 : Programmation avancée et synchronisation avec les sémaphores](./TP_2/RapportTP2.md)
- **Objectif** : Utilisation de sémaphores pour contrôler l'accès à des ressources partagées, assurant une gestion sécurisée des sections critiques dans un environnement multi-threadé.
- **Concepts abordés** :
    - Sémaphore binaire vs sémaphore général
    - Gestion de l'exclusion mutuelle
    - Création de classes de sémaphores en Java pour réguler l'accès aux ressources
- **Résumé** : Ce TP approfondit la synchronisation des threads à l’aide de sémaphores pour gérer des affichages séquentiels. Chaque thread doit attendre son tour avant d’accéder à une section critique, ce qui permet une gestion sécurisée des ressources partagées et la prévention des conflits d’accès.

## Utilisation

- Chaque TP inclut un rapport explicatif détaillant le code et les concepts théoriques.
- Les classes principales incluent des commentaires pour aider à la compréhension du code.

## Structure des fichiers

- **TP_1** : Contient le code et le rapport pour la gestion de threads avec des objets mobiles.
- **TP_2** : Contient le code et le rapport pour l'implémentation de sémaphores et la synchronisation d'affichages multi-threadés.

## Crédits

Projet réalisé par [Cyril TILAN](https://github.com/BluenessDev), étudiant en BUT Informatique à l'IUT de Velizy Villacoublay.

---

_**Note** : Ces projets ont été rédigés avec l'aide d'outils d'IA pour enrichir et structurer la documentation._