package com.alexit.justrecipes.data.sources

import com.alexit.justrecipes.data.model.IngredientModel


interface IngredientsSource {
    val listIngredients: MutableList<IngredientModel>
    val listInputtedIngredients: MutableList<IngredientModel>
}

class IngredientsSourceDB : IngredientsSource {
    override val listIngredients: MutableList<IngredientModel> = mutableListOf<IngredientModel>(
        IngredientModel(1, "помидор", null, "г", "овощи"),
        IngredientModel(2, "огурцы", null, "г", "овощи"),
        IngredientModel(3, "лук", null, "г", "овощи"),
        IngredientModel(4, "молоко", null, "мл", "молоко и сливки"),
        IngredientModel(5, "кефир", null, "мл", "кисломолочный напиток"),
        IngredientModel(6, "сыр адыгейский", null, "г", "сыр"),
        IngredientModel(7, "сырок плавленый", null, "г", "сыр"),
        IngredientModel(8, "фосоль", null, "г", "бобовые"),
        IngredientModel(9, "картофель", null, "кг", "овощи"),
        IngredientModel(10, "морковь", null, "г", "овощи"),
        IngredientModel(11, "перец", null, "г", "пряности"),
        IngredientModel(12, "перец болгарский", null, "г", "овощи"),
        IngredientModel(13, "свинина", null, "г", "мясо"),
        IngredientModel(14, "яйца", null, "шт", "яйца"),
        IngredientModel(15, "мука пшеничная", null, "г", "мука"),
        IngredientModel(16, "крахмал", null, "г", "специи"),
        IngredientModel(17, "соль", null, "г", "специи"),
        IngredientModel(18, "сахар", null, "г", "сахар"),
        IngredientModel(19, "укроп", null, "г", "зелень"),
        IngredientModel(20, "кабачки", null, "г", "овощи"),
        IngredientModel(21, "форель", null, "г", "рыба"),
        IngredientModel(22, "колбаса", null, "г", "колбаса"),
        IngredientModel(23, "макароны", null, "г", "макароны"),
        IngredientModel(24, "сосиски", null, "шт", "сосиски сардельки колбаски"),
        IngredientModel(25, "хлеб", null, "г", "хлебобулочный продукт"),
        IngredientModel(26, "свежевыловленная очищенная красная рыба без костей", null, "г", "рыба"),
        IngredientModel(27, "икра красная", null, "г", "икра"),
        IngredientModel(28, "морепродукты", null, "г", "морепродукты"),
        IngredientModel(29, "сметана 25%", null, "г", "кисломолочный продукт")
    )
    override val listInputtedIngredients: MutableList<IngredientModel> = mutableListOf<IngredientModel>(
        IngredientModel(1, "томаты консервированные в собственном соку без соли", null, "г", "овощи"),
        IngredientModel(2, "лосось", null, "г", "рыба"),
        IngredientModel(3, "свиная отбивная на косточке", null, "г", "мясо"),
        IngredientModel(4, "морковь по корейски", null, "г", "овощи"),
        IngredientModel(5, "баранина", null, "г", "мясо"),
        IngredientModel(6, "фасоль красная", null, "г", "бобовые"),
        IngredientModel(7, "маскарпоне", null, "г", "сыр"),
        IngredientModel(8, "батон", null, "г", "хлебобулочный продукт"),
    )
}

