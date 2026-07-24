package fxc.dev.app.ui.category

data class Plant(
    val commonName: String,
    val scientificName: String,
    val imageUrl: String
)

object PlantData {
    val categoriesMap = mapOf(
        "flowers" to listOf(
            Plant(
                "Corn plant",
                "Dracaena fragrans, Fragrant dracaena",
                "https://images.unsplash.com/photo-1596547609652-9cf5d8d76921?auto=format&fit=crop&q=80&w=300"
            ),
            Plant(
                "Flaming Katy",
                "Kalanchoe blossfeldiana, Kalanchoe",
                "https://images.unsplash.com/photo-1603436326446-74e2d65f3168?auto=format&fit=crop&q=80&w=300"
            ),
            Plant(
                "Chinese evergreen",
                "Aglaonema commutatum, Aglaonema",
                "https://images.unsplash.com/photo-1614594975525-e45190c55d0b?auto=format&fit=crop&q=80&w=300"
            ),
            Plant(
                "ZZ Plant",
                "Zamioculcas zamiifolia, Zanzibar gem",
                "https://images.unsplash.com/photo-1632207691143-643e2a9a9361?auto=format&fit=crop&q=80&w=300"
            ),
            Plant(
                "Wild Cherry",
                "Prunus avium, Bird cherry",
                "https://images.unsplash.com/photo-1522748906645-95d8adfd52c7?auto=format&fit=crop&q=80&w=300"
            ),
            Plant(
                "Hydrangea",
                "Hydrangea macrophylla, Hortensia",
                "https://images.unsplash.com/photo-1507269837356-6082329c2d3b?auto=format&fit=crop&q=80&w=300"
            ),
            Plant(
                "Peace lily",
                "Spathiphyllum wallisii, White sails",
                "https://images.unsplash.com/photo-1593696954577-ab3d39317b97?auto=format&fit=crop&q=80&w=300"
            )
        ),
        "vegetables" to listOf(
            Plant(
                "Tomato",
                "Solanum lycopersicum, Garden tomato",
                "https://images.unsplash.com/photo-1592924357228-91a4daadcfea?auto=format&fit=crop&q=80&w=300"
            ),
            Plant(
                "Carrot",
                "Daucus carota, Wild carrot",
                "https://images.unsplash.com/photo-1598170845058-32b9d6a5da37?auto=format&fit=crop&q=80&w=300"
            ),
            Plant(
                "Broccoli",
                "Brassica oleracea, Wild cabbage",
                "https://images.unsplash.com/photo-1584270354949-c26b0d5b4a0c?auto=format&fit=crop&q=80&w=300"
            ),
            Plant(
                "Spinach",
                "Spinacia oleracea, Common spinach",
                "https://images.unsplash.com/photo-1576045057995-568f588f82fb?auto=format&fit=crop&q=80&w=300"
            ),
            Plant(
                "Cucumber",
                "Cucumis sativus, Garden cucumber",
                "https://images.unsplash.com/photo-1449339864873-e48e67f1dad7?auto=format&fit=crop&q=80&w=300"
            )
        ),
        "cacti" to listOf(
            Plant(
                "Aloe Vera",
                "Aloe vera, True aloe",
                "https://images.unsplash.com/photo-1596547609652-9cf5d8d76921?auto=format&fit=crop&q=80&w=300"
            ),
            Plant(
                "Jade Plant",
                "Crassula ovata, Money tree",
                "https://images.unsplash.com/photo-1599599810769-bcde5a160d32?auto=format&fit=crop&q=80&w=300"
            ),
            Plant(
                "Zebra Cactus",
                "Haworthiopsis fasciata, Zebra haworthia",
                "https://images.unsplash.com/photo-1510137600163-2729bc6959a6?auto=format&fit=crop&q=80&w=300"
            ),
            Plant(
                "Golden Barrel Cactus",
                "Echinocactus grusonii, Golden ball",
                "https://images.unsplash.com/photo-1508849789987-4e5333c12b78?auto=format&fit=crop&q=80&w=300"
            )
        ),
        "tree" to listOf(
            Plant(
                "Oak Tree",
                "Quercus robur, English oak",
                "https://images.unsplash.com/photo-1502082553048-f009c37129b9?auto=format&fit=crop&q=80&w=300"
            ),
            Plant(
                "Maple Tree",
                "Acer rubrum, Red maple",
                "https://images.unsplash.com/photo-1507499739999-097706ad8914?auto=format&fit=crop&q=80&w=300"
            ),
            Plant(
                "Pine Tree",
                "Pinus sylvestris, Scots pine",
                "https://images.unsplash.com/photo-1542273917363-3b1817f69a2d?auto=format&fit=crop&q=80&w=300"
            )
        ),
        "vines" to listOf(
            Plant(
                "English Ivy",
                "Hedera helix, Common ivy",
                "https://images.unsplash.com/photo-1598880940080-ff9a29891b85?auto=format&fit=crop&q=80&w=300"
            ),
            Plant(
                "Golden Pothos",
                "Epipremnum aureum, Devil's ivy",
                "https://images.unsplash.com/photo-1597055181300-e3633a207518?auto=format&fit=crop&q=80&w=300"
            )
        ),
        "herbs" to listOf(
            Plant(
                "Basil",
                "Ocimum basilicum, Sweet basil",
                "https://images.unsplash.com/photo-1618164435735-413d3b066c9a?auto=format&fit=crop&q=80&w=300"
            ),
            Plant(
                "Mint",
                "Mentha spicata, Spearmint",
                "https://images.unsplash.com/photo-1600271886742-f049cd451bba?auto=format&fit=crop&q=80&w=300"
            ),
            Plant(
                "Rosemary",
                "Salvia rosmarinus, Anthos",
                "https://images.unsplash.com/photo-1594313598516-72f10d4b9b92?auto=format&fit=crop&q=80&w=300"
            )
        ),
        "shrubs" to listOf(
            Plant(
                "Boxwood",
                "Buxus sempervirens, Common box",
                "https://images.unsplash.com/photo-1599940824399-b87987ceb72a?auto=format&fit=crop&q=80&w=300"
            ),
            Plant(
                "Lilac",
                "Syringa vulgaris, Common lilac",
                "https://images.unsplash.com/photo-1560717789-0ac7c58ac90a?auto=format&fit=crop&q=80&w=300"
            )
        ),
        "foliage" to listOf(
            Plant(
                "Snake Plant",
                "Dracaena trifasciata, Mother-in-law's tongue",
                "https://images.unsplash.com/photo-1599599810769-bcde5a160d32?auto=format&fit=crop&q=80&w=300"
            ),
            Plant(
                "Monstera",
                "Monstera deliciosa, Swiss cheese plant",
                "https://images.unsplash.com/photo-1614594975525-e45190c55d0b?auto=format&fit=crop&q=80&w=300"
            ),
            Plant(
                "Spider Plant",
                "Chlorophytum comosum, Airplane plant",
                "https://images.unsplash.com/photo-1572085312347-19d8544d6a13?auto=format&fit=crop&q=80&w=300"
            )
        ),
        "air_cleaner" to listOf(
            Plant(
                "Peace Lily",
                "Spathiphyllum wallisii, White sails",
                "https://images.unsplash.com/photo-1593696954577-ab3d39317b97?auto=format&fit=crop&q=80&w=300"
            ),
            Plant(
                "Snake Plant",
                "Dracaena trifasciata, Mother-in-law's tongue",
                "https://images.unsplash.com/photo-1599599810769-bcde5a160d32?auto=format&fit=crop&q=80&w=300"
            )
        ),
        "easy_care" to listOf(
            Plant(
                "ZZ Plant",
                "Zamioculcas zamiifolia, Zanzibar gem",
                "https://images.unsplash.com/photo-1632207691143-643e2a9a9361?auto=format&fit=crop&q=80&w=300"
            ),
            Plant(
                "Snake Plant",
                "Dracaena trifasciata, Mother-in-law's tongue",
                "https://images.unsplash.com/photo-1599599810769-bcde5a160d32?auto=format&fit=crop&q=80&w=300"
            ),
            Plant(
                "Golden Pothos",
                "Epipremnum aureum, Devil's ivy",
                "https://images.unsplash.com/photo-1597055181300-e3633a207518?auto=format&fit=crop&q=80&w=300"
            )
        )
    )
}
