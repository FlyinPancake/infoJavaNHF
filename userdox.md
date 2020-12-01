# Felhasználói dokumentáció

## A játék szabályai

### Játékmenet

A játékban elemeket kell egymásra pakolni. Az elemek egyenletes sebességgel közelednek a "talajhoz" a pálya teteje felől. Esés közben az elemeket lehet jobbra-balra mozgatni, forgatni, akár az esésüket is gyorsítani, vagy azonnal letenni őket. Amint egy elemet letettünk, jön a következő, véletlenszerűen kiválasztott elem. Egy időben egyszerre egy elemet félretehetünk. Ezt az elemet bármikor kicserélhetjük az aktuálisan eső elemmel, majd ez a játéktér tetejéről indulva már ismert módon közlekedik. __A cél minél több pontot szerezni.__

### A játék vége

A játék kétféleképpen ér véget.

1. A játékos megszakítja

2. A játékos veszít, mert az új elem már nem helyezhető le szabályosan.

Mindkét esetben megkapja a játékos a pontjait és ezek után új játékba kezdhet.

## A játék vizuális felépítése

### Játéktér

Az ablak bal felső szélén van a játéktér. Itt folyik a játék lényege.

### Next és Hold ablakok

Ezek mutatják, hogy milyen elem a következő és milyen elemet tettünk félre.

Az ablak jobb szélén lévő sáv felső felében vannak.

### Menü

A Next és Hold ablakok alatt van, innen lehet

* Indítani - megállítani a játékot

* Szüneteltetni - folytatni a játékot 

* Megnézni a toplistát

* Kilépni

### Állapotsáv

Kiírja, hogy milyen állapotban van a játék:

* Ready - indítható a játék

* Paused - szünetel a játék

* Running - fut a játék

És emellett (szó szerint) kiírja az aktuálisan elért pontszámot.

## Irányítás

| Billentyű     | Játék                                  |
|:-------------:| -------------------------------------- |
| :arrow_left:  | balra mozgatja az elemet               |
| :arrow_right: | jobbra mozgatja az elemet              |
| :arrow_up:    | "Hard drop" azonnal leteszi az elemet  |
| :arrow_down:  | "Soft drop" egyel lépteti le az elemet |
| Q             | Balra fordítja az elemet               |
| E             | Jobbra fordítja az elemet              |
| SPACE         | félreteszi az elemet                   |

## A program futtatása

A program gyökérkönyvtárában futtassuk a `./gradlew run` parancsot unix kompatibilis rendszer alatt vagy `.\gradlew.bat run` parancsot Windows alatt és a játék elindul, illetve minden dependenciát beszerez magának.
