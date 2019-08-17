
cmake_minimum_required(VERSION 3.4.1)

set(SOURCES)
file(GLOB_RECURSE SOURCES ${CMAKE_SOURCE_DIR}/*.cpp ${CMAKE_SOURCE_DIR}/*.c )
add_library(
        native-lib
        SHARED
        ${SOURCES})

#set(LIBS)
#list(APPEND LIBS
#        jnigraphics
#        log)
find_library( # Sets the name of the path variable.
        log-lib
        log)

target_link_libraries(
        native-lib
        jnigraphics
        ${LIBS})