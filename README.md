<div align="center">
  <h1>Programmation Avancée et Qualité de Développement BUT Informatique 2024-2025</h1>
  <a href="https://fr.wikipedia.org/wiki/Java_(langage)"><img src="https://img.shields.io/badge/Java-red?style=for-the-badge&logo=visualstudiocode"/></a>
</div>

## À propos

Ce dépôt regroupe les rapports et codes de mes travaux pratiques en programmation avancée Java, focalisés sur les concepts de **programmation parallèle**, **gestion de threads** et **synchronisation**. Ces TP explorent des concepts essentiels comme les **threads**, les **sémaphores** et les **sections critiques**, avec des exemples concrets et des applications graphiques en Java.

## Introduction générale

La programmation parallèle est un domaine essentiel de l'informatique, permettant d'optimiser les performances des applications en exécutant des tâches simultanément. En Java, la gestion des threads et la synchronisation des accès aux ressources partagées sont des compétences clés pour développer des applications robustes et efficaces. Ces TP visent à explorer ces concepts en utilisant des exemples pratiques et des exercices concrets. Les rapports détaillant les étapes de chaque TP sont accompagnés de leur diagramme UML et de leur implémentation en Java. Ils sont trouvable dans les dossiers respectifs de chaque TP ou en cliquant sur les liens ci-dessous. 

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

### [TP 3 : Gestion de producteurs et consommateurs avec des boites aux lettres](./TP_3/RapportTP3.md)
- **Objectif** : Illustration d'un modèle producteur-consommateur pour gérer des ressources partagées, en utilisant des boites aux lettres pour synchroniser les threads producteurs et consommateurs.
- **Concepts abordés** :
    - Création de threads producteur et consommateur
    - Utilisation de verrous pour contrôler l'accès à des ressources partagées
    - Gestion de la synchronisation entre les threads
    - Utilisation de méthodes `wait()` et `notifyAll()` pour gérer l'attente et la notification des threads
- **Résumé** : Ce TP explore le modèle producteur-consommateur en Java, où un producteur ajoute des éléments à une boite aux lettres partagée et un consommateur les retire. La synchronisation des threads est essentielle pour éviter les conflits d'accès et garantir une gestion sécurisée des ressources partagées.

### [TP 4 : Monte Carlo, Parallélisation et Qualité de code](./TP_4/Rapport_Partie2.md)
- **Objectif** : Utilisation de la programmation parallèle pour accélérer le calcul de l'approximation de Pi par la méthode de Monte Carlo, en optimisant la qualité du code et la performance de l'application.
- **Concepts abordés** :
    - Parallélisation du calcul de Monte Carlo
    - Optimisation de la qualité du code
    - Mesure de la performance et de l'efficacité de l'application
    - Analyse des résultats et des améliorations possibles
    - Application des bonnes pratiques de programmation graces aux normes de qualité
- **Résumé** : Ce TP met en œuvre la parallélisation du calcul de Monte Carlo pour accélérer l'approximation de Pi. L'optimisation de la qualité du code et l'analyse des performances permettent d'identifier les points d'amélioration et de garantir une exécution efficace de l'application.

## Utilisation

- Chaque TP inclut un rapport explicatif détaillant le code et les concepts théoriques.
- Les classes principales incluent des commentaires pour aider à la compréhension du code.

## Structure des fichiers

- **TP_1** : Contient le code et le rapport pour la gestion de threads avec des objets mobiles.
- **TP_2** : Contient le code et le rapport pour l'implémentation de sémaphores et la synchronisation d'affichages multi-threadés.
- **TP_3** : Contient le code et le rapport pour la gestion de producteurs et consommateurs avec des boites aux lettres partagées.

## Conclusion générale

Ces TP en programmation avancée Java offrent une introduction pratique à la gestion des threads, à la synchronisation et à la programmation parallèle. Les exemples concrets et les exercices pratiques permettent de mieux comprendre les concepts théoriques et d'acquérir des compétences essentielles en programmation multi-thread. Les applications graphiques et les modèles de synchronisation abordés dans ces TP sont des éléments clés pour développer des applications robustes et performantes en Java.

## Crédits

Projet réalisé par [Cyril TILAN](https://github.com/BluenessDev), étudiant en BUT Informatique à l'IUT de Velizy Villacoublay.

---

_**Note** : Ces projets ont été rédigés avec l'aide d'outils d'IA pour enrichir et structurer la documentation._