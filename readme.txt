Auteurs:
	Karim Dahdouh
	Thierno Barry

Projet UE HMIN205 : Programmation Mobile et Projet MySchool (Ecole en Ligne)

====================================
Cahier des charges et fonctionnalités
=====================================


L’objectif de ce projet est de créer une application mobile sous Android pour proposer des
cours/formation en ligne. L’application schoolmouv constitue un exemple intéressant
https://www.schoolmouv.fr/, https://youtube.schoolmouv.fr/ à suivre pour développer cette
application.
Ainsi cette application doit permettre :
	De présenter les fonctionnalités de cette application.
	De s’inscrire suivant plusieurs profils.
	De bénéficier des services fonctionnels de cette application en fonction du profil de
	connexion et en fonction du mode de connexion : online, offline.
	Présentation de l’application. Une fois l’application installée et sans inscription, elle permet
	à son utilisateur de bénéficier de :
	- Présentation claire et ergonomique de l’application sous différents formats : vidéo,
	texte, etc.
	- Une démonstration (utilisation) réelle mais temporaire des services de l’application.
	- Une interface de connexion
	- Etc.
	Inscription. L’application permet de s’inscrire selon trois profils :
	-
	Parent : Dans ce cas, l’application lui propose :
	o De saisir les informations du parent : nom, prénom, choix login, choix mot de
	passe, adresse mail, compte Tweeter, Facebook, etc. Permettre de vérifier
	l’adresse mail (Par exemple, activation du compte via une lien envoyé à
	l’adresse mail en renseignée).
	o De choisir le nombre d’élèves concernés par cette inscription et de saisir leurs
	profils (nom, prénom, lien de parenté, niveau scolaire, etc.)
	o De choisir l’année ou les années scolaires concernées.
	o De choisir une formule parmi un ensemble de formules proposées. Par
	exemple :
	 1 cous par année scolaire.
	 2 cous par année scolaire.
	 Tous les cours d’une année scolaire.
	 Etc.
	o De choisir un mode de formation parmi deux :
	 Formule progression : Documents électroniques uniquement.
	 Formule accompagnement : accompagnement par un professeur
	(Accès à un tchat avec un enseignant pour poser ses questions dans
	toutes les matières.).



o Propose un mode de paiement : carte bancaire, prélèvement, etc. et se saisir les informations nécessaires à ce mode de paiement.
o Propose et envoie au parent (entre autres, par mail), des logins et mot de passe des élèves concernés. Les parents et les élèves peuvent ainsi se connecter en
utilisant ces informations.
Elève
	o Même étapes que « parent » avec la seule différence que l’élève sera
	enregistré sans parent.
	Connexion. Selon le profil de connexion, les fonctionnalités offertes seront différentes :
	
Parent
	o Visualiser les moments de connexion de l’élève.
	o Visualiser les cours et les exercices réalisés par l’élève.
	o Visualiser les durées d’activité sur l’application.
	o Visualiser des courbes de progression.
	o Visualiser des recommandations pour la progression de l’élève.
	§ Recommandations pour une progression adaptée à l’élève concerné.
	§ Recommandations et conseils généraux.
	o Définir des moments de rappel à l’élève pour revenir sur l’application.
	o Etc.
	Elève
	o Visualiser les différents articles de formation
	 Cours
		• Vidéo
		• Fiches de cours
		• Présentation
		 Exercices
	• QCM
	• Questions – Réponses
		 Des cours en temps réel avec possibilité de pose de question.
	• Ces cours sont annoncés préalablement par notification, mails,
	etc.
	§ Tchat
	§ Des recommandations pour la progression de l’élève.
	• Recommandations pour une progression adaptée à l’élève
	concerné.
	• Recommandations et conseils généraux.
	o Visualiser l’historique des activités sur l’application.
	o Etc.
	L’application permet deux modes de connexion : connexion online et connexion
	offline. La connexion online permet de se connecter au serveur et de bénéficier de tous
	les supports (cours, exercices, etc.) disponibles. En mode offline, l’utilisateur ne peut
	bénéficier que de certaines fonctionnalités en relation avec les données enregistrées
	localement. Une synchronisation doit être réalisée dès que l’utilisateur passe du mode
	offline au mode online.


Il est recommandé de réfléchir à la simplification des modes d’accès aux
fonctionnalités de l’application :
	o Utilisation de certains capteurs (exemple, accélération) pour activer ou
	désactiver certaines fonctionnalités.
	o Utilisation de la voix pour activer ou désactiver certaines fonctionnalités.
	o Etc.
Partie backend/serveur (Framework spring boot)
La partie serveur doit permettre de :
	- Stocker toutes les données nécessaires au fonctionnement de l’application, par
	exemple les comptes des utilisateurs, les cours sous leurs différents formats, etc.
	- Implémenter certains algorithmes nécessaires au fonctionnement de l’application
	mobile.


Partie Android
En plus des services mentionnés ci-dessus, il est vivement recommandé d’utiliser les
meilleures pratiques liées au développement Mobile (en Android) telles que Material Design,
les fragments, les préférences, les content provider, etc.
