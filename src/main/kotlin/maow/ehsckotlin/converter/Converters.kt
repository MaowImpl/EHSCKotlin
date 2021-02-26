package maow.ehsckotlin.converter

import maow.ehsckotlin.spec.PassOption
import org.dom4j.Document

class ExtensionConverter(document: Document) : Converter(document) {
    private fun code(value: String): String {
        return when (value.toLowerCase()) {
            "english" -> "en-us"
            "german" -> "de-de"
            "french" -> "fr-be"
            "russian" -> "ru-ru"
            "spanish" -> "es-ar"
            "korean" -> "ko-kr"
            "japanese" -> "ja-jp"
            "chinese" -> "zh-cn"
            else -> ""
        }
    }

    override fun convert() {
        doc {
            root("HacknetExtension") {
                attribute("lang") convert {
                    add(
                        element("Language", value()),
                        element("WorkshopLanguage", code(value()))
                    )}
                attribute("name") translate element("Name")
                attribute("allowSaves") translate element("AllowSaves")
                element("StartingNodes") translate element("VisibleStartingNodes")
                elements("StartingMission", "StartingActions", "Description", "WorkshopDescription") pass PassOption.ALL
                element("Description") convert {
                    if (element("WorkshopDescription") !in this)
                        add(element("WorkshopDescription", value()))
                }
                element("Faction") pass PassOption.ALL
                add(element("StartsWithTutorial", "false"))
                attribute("intro") translate element("HasIntroStartup")
                element("StartingMusic") translate element("IntroStartupSong")
                element("Sequencer") convert {
                    add(
                        attribute("target") convert element("SequencerTargetID"),
                        attribute("spinupTime") convert element("SequencerSpinUpTime"),
                        attribute("flag") convert element("SequencerFlagRequiredForStart"),
                        attribute("actions") convert element("ActionsToRunOnSequencerStart")
                    )
                }
                element("WorkshopInfo") convert {
                    add(
                        attribute("visibility") convert element("WorkshopVisibility"),
                        attribute("tags") convert element("WorkshopTags"),
                        attribute("logo") convert element("WorkshopPreviewImagePath")
                    )
                }
                add(element("WorkshopPublishID", "NONE"))
            }
        }
    }
}