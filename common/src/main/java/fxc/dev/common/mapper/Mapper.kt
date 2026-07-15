package fxc.dev.common.mapper

/**
 * Created by Thanh Quang on 18/07/2022.
 */
interface Mapper<E, D> {
    fun mapFromEntity(item: E): D
    fun mapToEntity(item: D): E
}