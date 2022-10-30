# ConwaysGOL
Projecte Conway's Game Of Life per als problemes dailyhacks2022

La carpeta src/ conté le codi en Java, el projecte sencer.
El fitxer conway.exe es l'executable generat a partir del projecte.

FUNCIONAMENT
A l'executar es te diferents modes:
Step - Cada vegada que es polsa el botó es fa un pas en el joc.
Run/Pause - Al prémer es fa córrer el programa contínuament (es poden canviar les velocitats).
Es te zoom in i zoom out
Visualitzar/amagar les línies divisòries.
I el mode RAINBOW...

La Seed presentada:
La seed que es vol presentar dona importància al "Color-Preseving" dels "gliders" en interactuar amb els "reflectors".
Els "gliders" (conwaylife.com/wiki/Glider) tenen una propietat, el "color". El "color" es una propietat que diu que el "glider" manté la configuració d'ell al llarg del camp. O sigui, el "glider" te 4 estats diferents, son simètrics a 2, i el color es l'estat del "glider". Llavors, el "Color-Preservin" vol dir que en tot el camp els "gliders" únicament son rotats, pero no "desfasats en la linia temporal", cada "glider" es una copia dels altres.
Considero que el "Color-Preserving" es de gran importància, si es volen construir sistemes, ja que al mantenir el "color" permet simplificar els "protocols" i facilitar concatenacions entre diferents elements que interactuen amb els "gliders".
Per tenir el "Color-Preserving" al rotar 90º el camí dels "gliders", he usat un "reflector" que te la propietat. Aquest "reflector" fa us de L.W.S.S. (LighWeight SpaceShip) (conwaylife.com/wiki/Lightweight_spaceship) per fer el rebot del "glider. Els L.W.S.S. no son "Color-Preserving" pero si al "glider" resultant se li aplica un "Pentadecathlon" (conwaylife.com/wiki/Pentadecathlon) torna al color inicial, ja que el "Pentadecathlon" no es "Color-Preserving", pero junts es cancel·len. El generador de L.W.S.S. utilitza "gliders" que al col·lisionar genera un L.W.S.S.. Es fa us de "LightWeight Emulator" (conwaylife.com/wiki/Lightweight_emulator) per filtrar un si i un no els L.W.S.S.S., i un altre per eliminar els residuals.
Llavors, arribats en aquest punt, es molt mes fàcil fer rebots de "gliders", ja que no es necessita un "reflector" per a cada "color" possible, ja que sempre es trobarà el mateix "color" per rebotar. Només és necessari un estat inicial de "reflector" (i el seu simètric per rebotar en direcció oposada).
Ara si, la Seed presentada es la demostració de l'explicació anterior. Es presenta una Seed on aleatòriament es fa un camí on s'inicialitza amb un "True Period-60 glider gun" ("True Period" significa que emet un "glider" cada vegada que fa una oscil·lació) (conwaylife.com/wiki/Period-60_glider_gun) i es finalitza amb un "Eater" (conwaylife.com/wiki/Eater_1). pel camí es fan rebots amb el sistema explicat anteriorment. Es pot comprovar com es compleix el "Color-Preserving".
