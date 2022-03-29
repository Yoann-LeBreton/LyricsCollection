
# Lyrics Collection

Application de consultation de musiques

## Fonctionnalités
- Récupération des données sur le splashscreen
- Vérification de l'expiration des données au splashscreen
- Stockage des données en base de données
- Flavor 'phone' en mode portrait avec bottom navigation bar, affichage de 3 écrans (Albums, Songs, Favoris)
- Flavor 'tablet' détection de l'orientation, affichage de la liste des Albums, au clic sur un album affichage des musiques appartenant à l'album
- Ecoute du 'dark mode' du téléphone pour adaptation du thème des écrans 
- Affichage d'une dialog de détails d'une chanson au clic sur la chanson

## Tech Stack

**Language:** Kotlin

**Architecture:** Clean Architecture, MVVM

**Database:** Room

**Injection:** Koin

**Librairies:** [Picasso](https://github.com/square/picasso)


## API Reference

#### Base URL

```http
  https://gist.githubusercontent.com/Yoann-LeBreton
```
#### Get all songs

```http
  GET /daf1366561d97547407d4f471ac85004/raw/efd91bd919d99404142408bd19a88571ce6c46e3/song_collection.json
```



## Screenshots

<img src="https://raw.githubusercontent.com/Yoann-LeBreton/LyricsCollection/develop/screenshots/Screenshot_1648586814.png" width="25%" height="25%">
<img src="https://raw.githubusercontent.com/Yoann-LeBreton/LyricsCollection/develop/screenshots/Screenshot_1648586823.png" width="25%" height="25%">
<img src="https://raw.githubusercontent.com/Yoann-LeBreton/LyricsCollection/develop/screenshots/Screenshot_1648586828.png" width="25%" height="25%">
<img src="https://raw.githubusercontent.com/Yoann-LeBreton/LyricsCollection/develop/screenshots/Screenshot_1648587105.png" width="25%" height="25%">
<img src="https://raw.githubusercontent.com/Yoann-LeBreton/LyricsCollection/develop/screenshots/Screenshot_1648587112.png" width="25%" height="25%">

