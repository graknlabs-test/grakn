#
# Copyright (C) 2021 Vaticle
#
# This program is free software: you can redistribute it and/or modify
# it under the terms of the GNU Affero General Public License as
# published by the Free Software Foundation, either version 3 of the
# License, or (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU Affero General Public License for more details.
#
# You should have received a copy of the GNU Affero General Public License
# along with this program.  If not, see <https://www.gnu.org/licenses/>.
#

load("@vaticle_dependencies//tool/checkstyle:rules.bzl", "checkstyle_test")
load("@vaticle_dependencies//builder/java:rules.bzl", "native_java_libraries")

native_java_libraries(
    name = "verification",
    srcs = glob(["*.java"]),
    visibility = ["//visibility:public"],
    native_libraries_deps = [
        "//:typedb",
        "//rocks:rocks",
        "//concept:concept",
        "//traversal:traversal",
        "//logic:logic",
        "//pattern:pattern",
        "//reasoner:reasoner",
    ],
    deps = [
        "//common",
        "@vaticle_typedb_common//:common",
        "@vaticle_typeql_lang_java//:typeql-lang",
        "@vaticle_typeql_lang_java//common",
        "@vaticle_typeql_lang_java//query",
        "@vaticle_typeql_lang_java//pattern",
    ],
)

checkstyle_test(
    name = "checkstyle",
    include = glob(["*"]),
    license_type = "agpl",
)
