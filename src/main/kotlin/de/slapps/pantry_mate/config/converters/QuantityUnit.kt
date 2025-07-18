package de.slapps.pantry_mate.config.converters

import de.slapps.pantry_mate.pantry_box.model.QuantityUnit
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import org.springframework.data.convert.WritingConverter

@WritingConverter
class QuantityUnitWritingConverter : Converter<QuantityUnit, String> {

    override fun convert(source: QuantityUnit): String {
        return source.name.uppercase()
    }
}

@ReadingConverter
class QuantityUnitReadingConverter : Converter<String, QuantityUnit> {

    override fun convert(source: String): QuantityUnit{
        return QuantityUnit.valueOf(source)
    }
}