package com.lushan.securedapp.data.mapper

import com.lushan.securedapp.data.remote.dto.SecureDataDto
import com.lushan.securedapp.data.remote.dto.SensitiveDataDto
import com.lushan.securedapp.domain.model.SecureData
import com.lushan.securedapp.domain.model.SensitiveData

/**
 * Maps a [SensitiveDataDto] to a [SensitiveData] domain model.
 * This function transforms the data transfer object received from the API
 * into a format suitable for use within the application's domain layer.
 *
 * @author Lushan Lakpriya
 * @since 29-08-2025
 */
fun SensitiveDataDto.toDomain(): SensitiveData {
    return SensitiveData(
        message = message,
        data = data.toDomain()
    )
}

fun SecureDataDto.toDomain(): SecureData {
    return SecureData(
        title = title,
        description = description
    )

}