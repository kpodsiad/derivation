package evo.derivation

import AnnotationTest.Izbushka
import evo.derivation.AnnotationTest.Baba

class AnnotationsTest extends munit.FunSuite {
    test("isbuzhka") {
        val cfg = summon[Config[Izbushka]]
        assertEquals(cfg.top.fieldRenaming, Config.snakeCase)
        assertEquals(cfg.top.fieldNames, Map("pupa" -> "lupa"))
        assertEquals(cfg.top.embedFields, Set("azaza"))
    }

    test("baba") {
        val cfg = summon[Config[Baba]]
        assertEquals(cfg.constructorRenaming, Config.snakeCase)
        assertEquals(cfg.constructors("Yaga").renamed, Some("Jaga"))
        assertEquals(cfg.constructors("Noga").fieldRenaming("AbCd"), "AbCd")
        assertEquals(cfg.constructors("Kostyanaya").fieldRenaming("AbCd"), "ab_cd")
    }
}

object AnnotationTest {
    @SnakeCase
    case class Izbushka(
        @Rename("lupa") pupa: String,
        @Embed azaza: Double,
    ) derives Config

    @SnakeCase
    enum Baba derives Config:
        @Rename("Jaga")
        case Yaga
        @SnakeCase
        case Kostyanaya(lolJe: String, kekJi: Double)
        case Noga(@Embed cheburek: Izbushka)
}
