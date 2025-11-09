package com.example.coletaplus.Classes

import org.osmdroid.util.GeoPoint

/**
 * Representa um ponto de coleta (Lixeira) com suas propriedades.
 *
 * @property cor O tipo/cor da lixeira (ex: "Azul", "Amarelo", "Verde").
 * @property loc As coordenadas geográficas (latitude e longitude) da lixeira.
 * @property descricao Informações adicionais, como o tipo de material que coleta (ex: "Coleta de papel e papelão.").
 */

class Lixeira (


    val cor: String,
    val loc: GeoPoint,
    val descricao: String


)